package org.cos.application.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;
import org.aspectj.apache.bcel.classfile.Code;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.po.UserRelation;
import org.cos.common.entity.data.req.*;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.RedisService;
import org.cos.common.redis.UserKey;
import org.cos.common.repository.AssetRepository;
import org.cos.common.repository.TransWebsiteRepository;
import org.cos.common.repository.UserRelationRepository;
import org.cos.common.repository.UserRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.cos.common.token.TokenManager;
import org.cos.common.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.tree.TreeNode;
import java.util.*;

@Service
@Slf4j
@Transactional
public class CosdService {

    @Autowired
    private BaseConfiguration baseConfiguration;
    @Autowired
    private TransWebsiteRepository transWebsiteRepository;
    @Autowired
    private AssetRepository assetRepository;



    public Result stakeForSL(CosdStakeForSLReq req){
        // todo 缺少对用户id
        TransWebsite transWebsite = new TransWebsite();
        if(!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
        }
        transWebsite.setTxid(req.getTxId());
        transWebsite.setTransType(req.getTransType());
        transWebsite.setFromUserId(req.getFromUserId());
        transWebsite.setFromAssetType(req.getFromAssetType());
        transWebsite.setFromAmount(req.getFromAmount());
        transWebsite.setToUserId(req.getToUserId());
        transWebsite.setToAssetType(req.getToAssetType());
        transWebsite.setToAmount(req.getToAmount());
        transWebsite.setCreateTime(new Date());
        transWebsite.setUpdateTime(new Date());
        transWebsite.setRemark(req.getRemark());
        try {
            transWebsiteRepository.insertTransWebsite(transWebsite);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        Asset asset = new Asset();

        switch (req.getTransType()){


            // 用户质押 COSD 到星光
            case 2:
                if (baseConfiguration.getSlAmount()>=req.getFromAmount()){
                    asset.setUserId(req.getFromUserId());
                    asset.setAssetType(4);
                    asset.setAssetStatus(2);
                    asset.setCreateTime(new Date());
                    asset.setUpdateTime(new Date());
                    asset.setRemark(req.getRemark());
                    try {
                        assetRepository.insertAsset(asset);
                    }catch (Exception e){
                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                }
                break;
            case 5:
                // todo 后面引入 web3j，从链上查询
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(4);
                // todo 此处的状态设为1000，是为了与其他状态区分，标志用户失效
                asset.setAssetStatus(1000);
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.updateAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            // 用户充值购买 EVIC 积分
            case 7:
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(3);
                asset.setAmount(req.getFromAmount()*100);
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.insertAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            // 用户提现 EVIC
            case 8:
                // 查询当前用户的资产
                Asset asset1 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getFromAssetType());
                if(req.getFromAmount()>asset1.getAmount()){
                    throw new GlobalException(CodeMsg.ASSET_WITHDRAW_ERROR);
                }
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(3);
                asset.setAmount(asset1.getAmount()-req.getFromAmount());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.updateAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_UPDATE_ERROR.fillArgs(e.getMessage()));
                }
                break;
            // 用户购买 NFT 盲盒
            case 9:
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(2);
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.insertAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            case 10:
                // todo 用户交易 NFT
                break;
            default:
                // 交易类型错误
                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TYPE_ERROR);
        }
        return Result.success();
    }
    // 用户购买 COSD，数据库只是记录，不会修改用户的资产
    public Result purchaseCOSD(CosdStakeForSLReq req){
        // trans_type 0
        // todo 缺少对用户id
        TransWebsite transWebsite = new TransWebsite();
        if(!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
        }
        transWebsite.setTxid(req.getTxId());
        transWebsite.setTransType(req.getTransType());
        transWebsite.setFromUserId(req.getFromUserId());
        transWebsite.setFromAssetType(req.getFromAssetType());
        transWebsite.setFromAmount(req.getFromAmount());
        transWebsite.setToUserId(req.getToUserId());
        transWebsite.setToAssetType(req.getToAssetType());
        transWebsite.setToAmount(req.getToAmount());
        transWebsite.setCreateTime(new Date());
        transWebsite.setUpdateTime(new Date());
        transWebsite.setRemark(req.getRemark());
        try {
            transWebsiteRepository.insertTransWebsite(transWebsite);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result unStakeForSL(CosdStakeForSLReq req){
        // trans_type
        // todo 缺少对用户id
        TransWebsite transWebsite = new TransWebsite();
        if(!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
        }
        transWebsite.setTxid(req.getTxId());
        transWebsite.setTransType(req.getTransType());
        transWebsite.setFromUserId(req.getFromUserId());
        transWebsite.setFromAssetType(req.getFromAssetType());
        transWebsite.setFromAmount(req.getFromAmount());
        transWebsite.setToUserId(req.getToUserId());
        transWebsite.setToAssetType(req.getToAssetType());
        transWebsite.setToAmount(req.getToAmount());
        transWebsite.setCreateTime(new Date());
        transWebsite.setUpdateTime(new Date());
        transWebsite.setRemark(req.getRemark());
        try {
            transWebsiteRepository.insertTransWebsite(transWebsite);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        Asset asset = new Asset();

        switch (req.getTransType()){


            // 用户质押 COSD 到星光
            case 2:
                if (baseConfiguration.getSlAmount()>=req.getFromAmount()){
                    asset.setUserId(req.getFromUserId());
                    asset.setAssetType(4);
                    asset.setAssetStatus(2);
                    asset.setCreateTime(new Date());
                    asset.setUpdateTime(new Date());
                    asset.setRemark(req.getRemark());
                    try {
                        assetRepository.insertAsset(asset);
                    }catch (Exception e){
                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                }
                break;
            // 用户从星光池中解押 COSD
            case 5:
                // todo 后面引入 web3j，从链上查询
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(4);
                // todo 此处的状态设为1000，是为了与其他状态区分，标志用户失效
                asset.setAssetStatus(1000);
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.updateAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            // 用户充值购买 EVIC 积分
            case 7:
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(3);
                asset.setAmount(req.getFromAmount()*100);
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.insertAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            // 用户提现 EVIC
            case 8:
                // 查询当前用户的资产
                Asset asset1 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getFromAssetType());
                if(req.getFromAmount()>asset1.getAmount()){
                    throw new GlobalException(CodeMsg.ASSET_WITHDRAW_ERROR);
                }
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(3);
                asset.setAmount(asset1.getAmount()-req.getFromAmount());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.updateAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_UPDATE_ERROR.fillArgs(e.getMessage()));
                }
                break;
            // 用户购买 NFT 盲盒
            case 9:
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(2);
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                try {
                    assetRepository.insertAsset(asset);
                }catch (Exception e){
                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            case 10:
                // todo 用户交易 NFT
                break;
            default:
                // 交易类型错误
                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TYPE_ERROR);
        }
        return Result.success();
    }


}

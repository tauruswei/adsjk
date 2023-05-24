package org.cos.application.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.dto.PoolUserTimeDTO;
import org.cos.common.entity.data.po.*;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.exception.GlobalException;
import org.cos.common.repository.*;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class TransWebsiteService {
    @Autowired
    private BaseConfiguration baseConfiguration;
    @Autowired
    private TransWebsiteRepository transWebsiteRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private PoolUserRepository poolUserRepository;
    @Autowired
    private NFTRepository nftRepository;
    @Autowired
    UserRepository userRepository;

    public Result transaction(CosdStakeForSLReq req) {
        if (ObjectUtils.isEmpty(userRepository.queryUserById(req.getFromUserId()))) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        if (ObjectUtils.isEmpty(userRepository.queryUserById(req.getToUserId()))) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        TransWebsite transWebsite = new TransWebsite();
        if (!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
        }
        transWebsite.setTxId(req.getTxId());
        transWebsite.setTransType(req.getTransType());
        transWebsite.setFromUserId(req.getFromUserId());
        transWebsite.setFromAssetType(req.getFromAssetType());
        transWebsite.setFromAmount(req.getFromAmount());
        transWebsite.setToUserId(req.getToUserId());
        transWebsite.setToAssetType(req.getToAssetType());
        transWebsite.setToAmount(req.getToAmount());
        transWebsite.setNftTokenId(req.getNftVo().getTokenId());
        transWebsite.setCreateTime(new Date());
        transWebsite.setUpdateTime(new Date());
        transWebsite.setRemark(req.getRemark());
        try {
            transWebsiteRepository.insertTransWebsite(transWebsite);
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        switch (req.getTransType()) {
            // 用户质押 COSD 到DEFI
            case 1:
                // 用户质押 COSD 到星光
            case 2:
                // 用户质押 COSD 到 club
            case 3:
                // 用户从 defi 解押 COSD
            case 4:
                // 用户从 星光 解押 COSD
            case 5:
                // 用户从 club 解押 COSD
            case 6:
                PoolUser poolUser1 = poolUserRepository.queryPoolUserByUserIdAndPoolId(req.getPoolId(), req.getFromUserId());
                if (ObjectUtils.isEmpty(poolUser1)) {
                    PoolUser poolUser = new PoolUser();
                    poolUser.setUserId(req.getFromUserId());
                    poolUser.setPoolId(req.getPoolId());
                    poolUser.setUpdateTime(new Date());
                    poolUser.setCreateTime(new Date());
                    try {
                        poolUser.setAmount(req.getFromAmount());
                        poolUserRepository.insertPoolUser(poolUser);
                    } catch (Exception e) {
                        throw new GlobalException(CodeMsg.POOL_USER_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                } else {
                    //todo
                    poolUser1.setUpdateTime(new Date());
                    poolUser1.setAmount(poolUser1.getAmount() + req.getFromAmount());
                    if (poolUser1.getAmount() < 0) {
                        throw new GlobalException(CodeMsg.POOL_USER_BALANCE_ERROR);
                    }
                    poolUserRepository.updatePoolUser(poolUser1);
                }
                if ((req.getPoolId() == CommonConstant.POOL_CLUB)) {
                    User user = new User();
                    user.setId(req.getFromUserId());
                    user.setUserType(poolUser1.getAmount() >= baseConfiguration.getClubAmount() ? 2 : 1);
                    user.setUpdateTime(new Date());
                    userRepository.updateUser(user);
                }
                break;
            // 用户充值购买 EVIC 积分、提现 EVIC 积分、用户购买 COSD
            case 0:
                // 用户购买 EVIC 积分
            case 7:
                Asset asset = new Asset();
                // 查询当前用户的资产
                Asset asset1 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getToAssetType());
                if (ObjectUtils.isEmpty(asset1)) {
//                    throw new GlobalException(CodeMsg.ASSET_NOT_EXIST_ERROR);
                    asset.setUserId(req.getFromUserId());
                    if (req.getFromAssetType() != CommonConstant.USDT) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    if ((req.getToAssetType() != CommonConstant.COSD) && (req.getToAssetType() != CommonConstant.EVIC)) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    asset.setAssetType(req.getToAssetType());
                    asset.setAmount(req.getToAmount());
                    asset.setCreateTime(new Date());
                    asset.setUpdateTime(new Date());
                    asset.setRemark(req.getRemark());
                    try {
                        assetRepository.insertAsset(asset);
                    } catch (Exception e) {
                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                } else {
                    asset1.setAmount(asset1.getAmount() + req.getToAmount());
                    if (asset1.getAmount() < 0) {
                        throw new GlobalException(CodeMsg.ASSET_ACOUNT_ERROR);
                    }
                    asset1.setUpdateTime(new Date());
                    if ((req.getToAssetType() != CommonConstant.COSD) && (req.getToAssetType() != CommonConstant.EVIC)) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    asset1.setAssetType(req.getToAssetType());
                    assetRepository.updateAsset(asset1);
                }
                break;
            // 用户提现 EVIC
            case 8:
                Asset asset2 = new Asset();
                // 查询当前用户的资产
                Asset asset3 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getFromAssetType());
                if (ObjectUtils.isEmpty(asset3)) {
//                    throw new GlobalException(CodeMsg.ASSET_NOT_EXIST_ERROR);
                    asset2.setUserId(req.getFromUserId());
                    if (req.getFromAssetType() != CommonConstant.USDT) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    if ((req.getToAssetType() != CommonConstant.COSD) && (req.getToAssetType() != CommonConstant.EVIC)) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    asset2.setAssetType(req.getToAssetType());
                    asset2.setAmount(req.getToAmount());
                    asset2.setCreateTime(new Date());
                    asset2.setUpdateTime(new Date());
                    asset2.setRemark(req.getRemark());
                    try {
                        assetRepository.insertAsset(asset2);
                    } catch (Exception e) {
                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                } else {
                    asset3.setAmount(asset3.getAmount() + req.getFromAmount());
                    if (asset3.getAmount() < 0) {
                        throw new GlobalException(CodeMsg.ASSET_ACOUNT_ERROR);
                    }
                    asset3.setUpdateTime(new Date());
                    try {
                        assetRepository.updateAsset(asset3);
                    } catch (Exception e) {
                        throw new GlobalException(CodeMsg.ASSET_UPDATE_ERROR.fillArgs(e.getMessage()));
                    }
                }
                break;
            // 用户购买 NFT 盲盒
            case 9:
                NFT nft = new NFT();
                if (ObjectUtils.isNotEmpty(nftRepository.queryNFTByTokenId(req.getNftVo().getTokenId()))) {
                    throw new GlobalException(CodeMsg.NFT_EXIST_ERROR);
                }
                nft.setUserId(req.getFromUserId());
                nft.setStatus(CommonConstant.NFT_PURCHASED);
                nft.setTokenId(req.getNftVo().getTokenId());
                nft.setAttr1(req.getNftVo().getAttr1());
                nft.setAttr2(req.getNftVo().getAttr2());
                nft.setTxid(req.getTxId());
                nft.setGameType(req.getNftVo().getGameType());
                nft.setCreateTime(new Date());
                nft.setUpdateTime(new Date());
                try {
                    nftRepository.insertNFT(nft);
                } catch (Exception e) {
                    throw new GlobalException(CodeMsg.NFT_ADD_ERROR.fillArgs(e.getMessage()));
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

    // 查看用户是否可以解押
    public Result queryUserIsAbleForUnStake(Long userId) {
        List<PoolUserTimeDTO> results = poolUserRepository.queryPoolUserByUserIdForTime(userId);

        long timestamp = System.currentTimeMillis() / 1000;
        results.forEach(result -> {
            Long createTime = (result.getPoolUserCreateTime());
//            result.setCreateTime(createTime);
            // 星光 质押池没有锁仓
            if (ObjectUtils.isEmpty(result.getLockTime())) {
                result.setFlag(true);
                return;
            }
            // defi 锁仓有一个固定的 deadline
            if (result.getPoolType() == (CommonConstant.POOL_DEFI)) {
                if (timestamp >= result.getPoolStartTime() + result.getLockTime()) {
                    result.setFlag(true);
                } else {
                    result.setFlag(false);
                }
                return;
            }
            // 俱乐部质押池 锁仓90天，用户首次质押，需要质押90天
            if (timestamp >= (createTime + result.getLockTime())) {
                result.setFlag(true);
            } else {
                result.setFlag(false);
            }
        });
        return Result.success(results);
    }

//    // 用户购买 COSD，数据库只是记录，不会修改用户的资产
//    public Result purchaseCOSD(CosdStakeForSLReq req) {
//        // trans_type 0
//        // todo 缺少对用户id
//        TransWebsite transWebsite = new TransWebsite();
//        if (!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
//        }
//        transWebsite.setTxid(req.getTxId());
//        transWebsite.setTransType(req.getTransType());
//        transWebsite.setFromUserId(req.getFromUserId());
//        transWebsite.setFromAssetType(req.getFromAssetType());
//        transWebsite.setFromAmount(req.getFromAmount());
//        transWebsite.setToUserId(req.getToUserId());
//        transWebsite.setToAssetType(req.getToAssetType());
//        transWebsite.setToAmount(req.getToAmount());
//        transWebsite.setCreateTime(new Date());
//        transWebsite.setUpdateTime(new Date());
//        transWebsite.setRemark(req.getRemark());
//        try {
//            transWebsiteRepository.insertTransWebsite(transWebsite);
//        } catch (Exception e) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
//        }
//        return Result.success();
//    }

//    public Result unStakeForSL(CosdStakeForSLReq req) {
//        // trans_type
//        // todo 缺少对用户id
//        TransWebsite transWebsite = new TransWebsite();
//        if (!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
//        }
//        transWebsite.setTxid(req.getTxId());
//        transWebsite.setTransType(req.getTransType());
//        transWebsite.setFromUserId(req.getFromUserId());
//        transWebsite.setFromAssetType(req.getFromAssetType());
//        transWebsite.setFromAmount(req.getFromAmount());
//        transWebsite.setToUserId(req.getToUserId());
//        transWebsite.setToAssetType(req.getToAssetType());
//        transWebsite.setToAmount(req.getToAmount());
//        transWebsite.setCreateTime(new Date());
//        transWebsite.setUpdateTime(new Date());
//        transWebsite.setRemark(req.getRemark());
//        try {
//            transWebsiteRepository.insertTransWebsite(transWebsite);
//        } catch (Exception e) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
//        }
//        Asset asset = new Asset();
//
//        switch (req.getTransType()) {
//
//
//            // 用户质押 COSD 到星光
//            case 2:
//                if (baseConfiguration.getSlAmount() >= req.getFromAmount()) {
//                    asset.setUserId(req.getFromUserId());
//                    asset.setAssetType(4);
//                    asset.setAssetStatus(2);
//                    asset.setCreateTime(new Date());
//                    asset.setUpdateTime(new Date());
//                    asset.setRemark(req.getRemark());
//                    try {
//                        assetRepository.insertAsset(asset);
//                    } catch (Exception e) {
//                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                    }
//                }
//                break;
//            // 用户从星光池中解押 COSD
//            case 5:
//                // todo 后面引入 web3j，从链上查询
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(4);
//                // todo 此处的状态设为1000，是为了与其他状态区分，标志用户失效
//                asset.setAssetStatus(1000);
//                asset.setCreateTime(new Date());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.updateAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            // 用户充值购买 EVIC 积分
//            case 7:
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(3);
//                asset.setAmount(req.getFromAmount() * 100);
//                asset.setCreateTime(new Date());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.insertAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            // 用户提现 EVIC
//            case 8:
//                // 查询当前用户的资产
//                Asset asset1 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getFromAssetType());
//                if (req.getFromAmount() > asset1.getAmount()) {
//                    throw new GlobalException(CodeMsg.ASSET_WITHDRAW_ERROR);
//                }
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(3);
//                asset.setAmount(asset1.getAmount() - req.getFromAmount());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.updateAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_UPDATE_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            // 用户购买 NFT 盲盒
//            case 9:
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(2);
//                asset.setCreateTime(new Date());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.insertAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            case 10:
//                // todo 用户交易 NFT
//                break;
//            default:
//                // 交易类型错误
//                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TYPE_ERROR);
//        }
//        return Result.success();
//    }


}

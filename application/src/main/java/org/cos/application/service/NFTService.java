package org.cos.application.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.convert.WebNFTVoConvert;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.req.NFTListReq;
import org.cos.common.entity.data.req.NFTPurchaseReq;
import org.cos.common.entity.data.req.NFTUpdateReq;
import org.cos.common.entity.data.vo.WebNFTVo;
import org.cos.common.exception.GlobalException;
import org.cos.common.repository.AssetRepository;
import org.cos.common.repository.NFTRepository;
import org.cos.common.repository.TransWebsiteRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class NFTService {

    @Autowired
    private BaseConfiguration baseConfiguration;
    @Autowired
    private NFTRepository nftRepository;
    @Autowired
    private TransWebsiteRepository transWebsiteRepository;
    @Autowired
    private AssetRepository assetRepository;

    public Result purchaseNFT(NFTPurchaseReq req){
        // todo 缺少对用户id
        TransWebsite transWebsite = new TransWebsite();
        if(!Objects.isNull(nftRepository.queryTransWebsiteByTxId(req.getTxId()))){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
        }
        transWebsite.setTxId(req.getTxId());
        transWebsite.setTransType(req.getTransType());
        transWebsite.setFromUserId(req.getFromUserId());
        transWebsite.setFromAssetType(req.getFromAssetType());
        transWebsite.setFromAmount(req.getFromAmount());
        transWebsite.setCreateTime(new Date());
        transWebsite.setUpdateTime(new Date());
        transWebsite.setRemark(req.getRemark());
        try {
            transWebsiteRepository.insertTransWebsite(transWebsite);
        }catch (Exception e){
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        Asset asset = new Asset();
        NFT nft = new NFT();

        switch (req.getTransType()){
            // 用户购买 NFT 盲盒
            case 9:
                asset.setUserId(req.getFromUserId());
                asset.setAssetType(2);
                asset.setAmount(1);
                asset.setAssetStatus(0);
                asset.setAssetAttrs(JSONObject.toJSONString(req.getNftAttribute()));
                asset.setCreateTime(new Date());
                asset.setUpdateTime(new Date());
                asset.setRemark(req.getRemark());
                nft.setUserId(req.getFromUserId());
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

    public Result queryNFTsByUserIdAndStatus(NFTListReq req){

        if((null!=req.getPageNo())&&(null!=req.getPageSize())){
            PageHelper.startPage(req.getPageNo(),req.getPageSize());
        }else{
            PageHelper.startPage(1,10);
        }
        NFT nft = new NFT();
        if(ObjectUtils.isNotEmpty(req.getUserId())){

            nft.setUserId(req.getUserId());
        }
        if(ObjectUtils.isNotEmpty(req.getStatus())){

            nft.setStatus(req.getStatus());
        }
        List<NFT> nfts = nftRepository.queryNFTsByUserIdAndStatus(nft);

        PageInfo<NFT> pageInfo = new PageInfo<>(nfts);

//        pageInfo.setList(WebNFTVoConvert.WebNFTVoListConvert(pageInfo.getList()));

        PageInfo<WebNFTVo> pageInfo1 = new PageInfo<>();
        pageInfo1.setList(WebNFTVoConvert.WebNFTVoListConvert(pageInfo.getList()));
        pageInfo1.setTotal(pageInfo.getTotal());


        return Result.success(pageInfo1);
    }

    public Result queryNFTsByUserIdAndStatusGame(NFTListReq req){

//        if((null!=req.getPageNo())&&(null!=req.getPageSize())){
//            PageHelper.startPage(req.getPageNo(),req.getPageSize());
//        }else{
//            PageHelper.startPage(1,10);
//        }
        NFT nft = new NFT();
        nft.setUserId(req.getUserId());
        nft.setStatus(req.getStatus());
        List<NFT> nfts = nftRepository.queryNFTsByUserIdAndStatus(nft);

//        PageInfo<NFT> pageInfo = new PageInfo<>(nfts);

        return Result.success(nfts);
    }

    public Result updateNFTStatus(NFTUpdateReq req){
        NFT nft = nftRepository.queryNFTByTokenId(req.getTokenId());
        if(ObjectUtils.isEmpty(nft)){
            throw new GlobalException(CodeMsg.NFT_NOT_EXIST_ERROR);
        }
        nft.setStatus(req.getStatus());
        nftRepository.updateNFTStatus(nft);
        return Result.success(nft);
    }

    public Result queryNFTByUserIdAndAtrr1(NFTListReq req){

        NFT nft = new NFT();
        nft.setUserId(req.getUserId());
        nft.setStatus(req.getStatus());
        NFT nft1 = nftRepository.queryNFTByUserIdAndAtrr1(nft);

        return Result.success(nft1);
    }

}

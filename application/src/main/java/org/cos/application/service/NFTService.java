package org.cos.application.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.convert.WebNFTVoConvert;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.req.NFTListReq;
import org.cos.common.entity.data.req.NFTPurchaseReq;
import org.cos.common.entity.data.req.NFTUpdateReq;
import org.cos.common.entity.data.vo.WebNFTVo;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.NFTKey;
import org.cos.common.redis.RedisService;
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
    @Autowired
    private RedisService redisService;

    public static String luna_script = "if redis.call('exists', KEYS[1]) == 1 then " +
            "local nft = redis.call('get', KEYS[1]); " +
            "local nftJson = cjson.decode(nft); " +
            "nftJson['status'] = ARGV[1]; " +
            "redis.call('set', KEYS[1], cjson.encode(nftJson)); " +
            "return 1; " +
            "end; " +
            "return 0;";

    public Result purchaseNFT(NFTPurchaseReq req) {
        // todo 缺少对用户id
        TransWebsite transWebsite = new TransWebsite();
        if (!Objects.isNull(nftRepository.queryTransWebsiteByTxId(req.getTxId()))) {
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
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        Asset asset = new Asset();
        NFT nft = new NFT();

        switch (req.getTransType()) {
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
                } catch (Exception e) {
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

    public Result queryNFTsByUserIdAndStatus(NFTListReq req) {

        if ((null != req.getPageNo()) && (null != req.getPageSize())) {
            PageHelper.startPage(req.getPageNo(), req.getPageSize());
        } else {
            PageHelper.startPage(1, 10);
        }
        NFT nft = new NFT();
        if (ObjectUtils.isNotEmpty(req.getUserId())) {

            nft.setUserId(req.getUserId());
        }
        if (ObjectUtils.isNotEmpty(req.getStatus())) {

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

    public Result queryNFTsByUserIdAndStatusGame(NFTListReq req) {

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

    public Result updateNFTStatus(NFTUpdateReq req) {
//        NFT nft = nftRepository.queryNFTByTokenId(req.getNftVo().getTokenId());
//        if(ObjectUtils.isNotEmpty(nft)){
//            nft.setStatus(req.getNftVo().getStatus());
//            nftRepository.updateNFTStatus(nft);
//            return Result.success(nft);
//        }
//        NFTVo nftVo = redisService.get(NFTKey.getTokenId, req.getNftVo().getTokenId(), NFTVo.class);
//        if(ObjectUtils.isNotEmpty(nftVo)){
//            throw new GlobalException(CodeMsg.NFT_REFRESH_LATER_ERROR);
//        }else{
        ;
        // 在 active 列表的 nft，点击 use it for game，分两种情况：（1） 如果 nft 的key，存在 redis 中，说明 nft 是在官网购买的，需要等待，防止交易回滚,
        //                                                    （2） 如果 redis 中不存在，则直接入库
        Boolean exist = redisService.evalSet(NFTKey.getTokenId, req.getNftVo().getTokenId(), luna_script, CommonConstant.NFT_USED);
        if (exist){
            return Result.success("");
        }

        // 在 active 列表的 nft，点击 use it for game，直接入库
        NFT nft = nftRepository.queryNFTByTokenId(req.getNftVo().getTokenId());
        if(ObjectUtils.isNotEmpty(nft)){
            throw new GlobalException(CodeMsg.NFT_EXIST_ERROR);
        }
        NFT nft1 = new NFT();
        nft1.setStatus(CommonConstant.NFT_USED);
        nft1.setCreateTime(new Date());
        nft1.setUpdateTime(new Date());
        nft1.setTokenId(req.getNftVo().getTokenId());
        nft1.setUserId(req.getUserId());
        nft1.setAttr1(req.getNftVo().getAttr1());
        nft1.setAttr2(req.getNftVo().getAttr2());
        nft1.setUpchainTime(req.getNftVo().getTime());
        nftRepository.insertNFT(nft1);
        return Result.success(nft1);
//        }
    }

    public Result queryNFTByUserIdAndAtrr1(NFTListReq req) {

        NFT nft = new NFT();
        nft.setUserId(req.getUserId());
        nft.setStatus(req.getStatus());
        NFT nft1 = nftRepository.queryNFTByUserIdAndAtrr1(nft);

        return Result.success(nft1);
    }

}

package org.cos.application.service;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.convert.GameVoConvert;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.PoolUser;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.req.AssetQueryReq;
import org.cos.common.entity.data.req.NFTListReq;
import org.cos.common.entity.data.vo.UserGameVo;
import org.cos.common.exception.GlobalException;
import org.cos.common.repository.AssetRepository;
import org.cos.common.repository.NFTRepository;
import org.cos.common.repository.PoolUserRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.cos.common.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@Transactional
public class GameService {
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    NFTRepository nftRepository;
    @Autowired
    AssetService assetService;
    @Autowired
    NFTService nftService;
    @Autowired
    UserService userService;
    @Autowired
    PoolUserRepository poolUserRepository;
    @Autowired
    BaseConfiguration baseConfiguration;
    public Result increaseUserMoney(Long userId,int moneyId,double moneyAmount){
        moneyId = moneyId==1?CommonConstant.EVIC:CommonConstant.SL;
        List<Asset> assets = assetRepository.queryAssetsByUserId(userId, moneyId);
        if (ObjectUtils.isEmpty(assets)){
            Asset asset = new Asset();
            asset.setAssetType(moneyId);
            asset.setUserId(userId);
            asset.setAmount(moneyAmount);
            asset.setCreateTime(new Date());
            asset.setUpdateTime(new Date());
            assetRepository.insertAsset(asset);
        }else{
            Asset asset = assets.get(0);
            asset.setAmount(asset.getAmount()+moneyAmount);
            if(asset.getAmount()<0){
                throw new GlobalException(CodeMsg.ASSET_AMOUNT_ERROR);
            }
            assetRepository.updateAsset(asset);
        }

        // 查询用户信息
        Result<User> result = userService.queryUserById(userId);
        User user = result.getData();

        String jwt = TokenManager.createJWT(user.getName(), Integer.parseInt(baseConfiguration.getTokenTimeOut()), "", new HashMap<>());

        // 获取用户资产
        AssetQueryReq assetQueryReq = new AssetQueryReq();
        assetQueryReq.setUserId(userId);
        assetQueryReq.setAssetType(0);
        Result<List<Asset>> assets1 = assetService.queryUserAssets(assetQueryReq);

        // 获取用户是否具有玩星光的资格
        PoolUser poolUser = poolUserRepository.queryPoolUserByUserIdAndPoolId((long) CommonConstant.POOL_SL, user.getId());
        if(ObjectUtils.isEmpty(poolUser)){
            poolUser = new PoolUser();
        }
        // 获取用户的NFT
        NFTListReq nftListReq = new NFTListReq();
        nftListReq.setUserId(userId);
        // 查找已使用的 NFT
        nftListReq.setStatus(1);

        Result<List<NFT>> nfts = nftService.queryNFTsByUserIdAndStatusGame(nftListReq);

        UserGameVo userGameVo = GameVoConvert.UserGameVoConvert(jwt, user, assets1.getData(), nfts.getData(),poolUser.getAmount()>baseConfiguration.getSlAmount()?true:false);

        return Result.success(userGameVo);
    }

    public Result increaseUserChesserPts(Long userId,int chesserId,int ptsInc){
        NFT nft = new NFT();
        nft.setUserId(userId);
        nft.setAttr1(chesserId+"");
        nft.setStatus(CommonConstant.NFT_USED);
        NFT nft1 = nftRepository.queryNFTByUserIdAndAtrr1(nft);
        if (ObjectUtils.isEmpty(nft1)){
            throw new GlobalException(CodeMsg.ASSET_NOT_EXIST_ERROR);
        }
        nft1.setAttr2(Integer.parseInt(nft1.getAttr2())+ptsInc+"");
        if (Integer.parseInt(nft1.getAttr2())==0){
            nft1.setStatus(CommonConstant.NFT_INEFFECTIVE);
        }
        if (Integer.parseInt(nft1.getAttr2())<0){
            throw new GlobalException(CodeMsg.NFT_CHANCE_NOT_BE_NEGATIVE);
        }
        nftRepository.updateNFTStatus(nft1);

        // 查询用户信息
        Result<User> result = userService.queryUserById(userId);
        User user = result.getData();
        String jwt = TokenManager.createJWT(user.getName(), Integer.parseInt(baseConfiguration.getTokenTimeOut()), "", new HashMap<>());


        // 获取用户资产
        AssetQueryReq assetQueryReq = new AssetQueryReq();
        assetQueryReq.setUserId(userId);
        assetQueryReq.setAssetType(0);
        Result<List<Asset>> assets1 = assetService.queryUserAssets(assetQueryReq);

        // 获取用户是否具有玩星光的资格
        PoolUser poolUser = poolUserRepository.queryPoolUserByUserIdAndPoolId((long) CommonConstant.POOL_SL, user.getId());
        if(ObjectUtils.isEmpty(poolUser)){
            poolUser = new PoolUser();
        }

        // 获取用户的NFT
        NFTListReq nftListReq = new NFTListReq();
        nftListReq.setUserId(userId);
        // 查找已使用的 NFT
        nftListReq.setStatus(1);

        Result<List<NFT>> nfts = nftService.queryNFTsByUserIdAndStatusGame(nftListReq);

        UserGameVo userGameVo = GameVoConvert.UserGameVoConvert(jwt, user, assets1.getData(), nfts.getData(),poolUser.getAmount()>baseConfiguration.getSlAmount()?true:false);
        return Result.success(userGameVo);
    }
}

package org.cos.application.service;

import lombok.extern.slf4j.Slf4j;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.req.AssetQueryReq;
import org.cos.common.exception.GlobalException;
import org.cos.common.repository.AssetRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    public Result queryUserAssets(AssetQueryReq req) {
        List<Asset> assets = assetRepository.queryAssetsByUserId(req.getUserId(), req.getAssetType());

        return Result.success(assets);
    }

    public Result queryUserAsset(AssetQueryReq req) {
        List<Asset> assets = assetRepository.queryAssetsByUserId(req.getUserId(), req.getAssetType());
        if(assets.isEmpty()){
            throw  new GlobalException(CodeMsg.ASSET_NOT_EXIST_ERROR);
        }

        return Result.success(assets.get(0));
    }
}

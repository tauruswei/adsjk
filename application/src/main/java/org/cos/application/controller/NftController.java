package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.NFTService;
import org.cos.common.entity.data.req.NFTListReq;
import org.cos.common.entity.data.req.NFTPurchaseReq;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "NFT controller",tags = "NFT 模块", description = "NFT 模块 Rest API")
@RequestMapping("nft")
@RestController
@Slf4j
public class NftController {
    @Autowired
    NFTService nftService;
    @ApiOperation("用户购买NFT盲盒")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("purchaseNFT")
    public Result purchaseNFT(@Validated @RequestBody NFTPurchaseReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return nftService.purchaseNFT(req);
    }

    @ApiOperation("用户 NFT 列表查询")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("queryNFTsByUserIdAndStatus")
    public Result queryNFTsByUserIdAndStatus(@Validated @RequestBody NFTListReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return nftService.queryNFTsByUserIdAndStatus(req);
    }
}

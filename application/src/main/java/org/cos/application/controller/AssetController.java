package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.AssetService;
import org.cos.common.entity.data.req.AssetQueryReq;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "Asset controller",tags = "Asset 模块", description = "Asset 模块 Rest API")
@RequestMapping("asset")
@RestController
@Slf4j
public class AssetController {
    @Autowired
    private AssetService evicService;
    @ApiOperation("查询用户的同质化资产")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping ("queryUserAssets")
    public Result queryUserAssets(@Validated @RequestBody AssetQueryReq req) {
        return evicService.queryUserAssets(req);
    }
}

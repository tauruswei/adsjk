package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.cos.application.service.CosdService;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.entity.data.req.UserLoginReq;
import org.cos.common.result.Result;
import org.cos.common.result.Result1;
import org.cos.common.result.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "COSD controller",tags = "COSD 模块", description = "COSD 模块 Rest API")
@RequestMapping("cosd")
@RestController
@Slf4j
public class CosdController {
    @Autowired
    CosdService cosdService;
    @ApiOperation("用户质押 COSD 获得玩星光的资格")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("stakeForSL")
    public Result stakeForSL(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.stakeForSL(req);
    }

    @ApiOperation("用户解押星光池中的 COSD")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("unStakeForSL")
    public Result unStakeForSL(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.unStakeForSL(req);
    }

    @ApiOperation("用户质押 COSD 到 DEFI")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("stakeForDefi")
    public Result stakeForDefi(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.stakeForSL(req);
    }

    @ApiOperation("用户解押 DEFI 池中的 COSD")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("unStakeForDefi")
    public Result unStakeForDefi(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.stakeForSL(req);
    }

    @ApiOperation("俱乐部老板质押 COSD ")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("stakeForClub")
    public Result stakeForClub(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.stakeForSL(req);
    }
    @ApiOperation("俱乐部老板解押 COSD ")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("unstakeForClub")
    public Result unStakeForClub(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.stakeForSL(req);
    }
    @ApiOperation("用户购买 COSD ")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("purchaseCOSD")
    public Result purchaseCOSD(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return cosdService.purchaseCOSD(req);
    }


}

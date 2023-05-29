package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.TransWebsiteService;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "Web transaction controller",tags = "web transaction 模块", description = "web transaction 模块 Rest API")
@RequestMapping("webTransaction")
@RestController
@Slf4j
public class TransWebsiteController {
    @Autowired
    TransWebsiteService webTranService;
    @ApiOperation("用户在网站的交易存库")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("save")
    public Result transaction(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return webTranService.transactionAsync(req);
    }
    @ApiOperation("查询用户是否可以提现")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("queryUserIsAbleForUnStake")
    public Result queryUserIsAbleForUnStake(@RequestParam(name="userId") Long userId) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return webTranService.queryUserIsAbleForUnStake(userId);
    }

    @ApiOperation("用户提现evic")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("withdrawEvic")
    public Result withdrawEvic(@Validated @RequestBody CosdStakeForSLReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return webTranService.withdrawEvic(req);
    }

//    @ApiOperation("用户解押星光池中的 COSD")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("unStakeForSL")
//    public Result unStakeForSL(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.unStakeForSL(req);
//    }
//
//    @ApiOperation("用户质押 COSD 到 DEFI")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("stakeForDefi")
//    public Result stakeForDefi(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.stakeForSL(req);
//    }
//
//    @ApiOperation("用户解押 DEFI 池中的 COSD")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("unStakeForDefi")
//    public Result unStakeForDefi(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.stakeForSL(req);
//    }
//
//    @ApiOperation("俱乐部老板质押 COSD ")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("stakeForClub")
//    public Result stakeForClub(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.stakeForSL(req);
//    }
//    @ApiOperation("俱乐部老板解押 COSD ")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("unstakeForClub")
//    public Result unStakeForClub(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.stakeForSL(req);
//    }
//    @ApiOperation("用户购买 COSD ")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("purchaseCOSD")
//    public Result purchaseCOSD(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.purchaseCOSD(req);
//    }
//
//    @ApiOperation("用户购买 艾维克 ")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("purchaseEVIC")
//    public Result purchaseEVIC(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.purchaseCOSD(req);
//    }
//
//    @ApiOperation("用户提现 艾维克 ")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
//    @PostMapping("withDrawEVIC")
//    public Result withDrawEVIC(@Validated @RequestBody CosdStakeForSLReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return cosdService.purchaseCOSD(req);
//    }






}

package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.UserService;
import org.cos.common.entity.data.req.*;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(value = "用户controller",tags = "用户组模块", description = "用户组模块 Rest API")
@RequestMapping("user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("发送验证码")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("sendCode")
    public Result sendCode(@Validated @RequestBody UserSendCodeReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.sendCode(req);
    }

    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("register")
    public Result createUser(@Validated @RequestBody UserCreateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.createUser(req);
    }

    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("login")
    public Result login(@Validated @RequestBody UserLoginReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.login(req);
    }


    @ApiOperation("根据用户id 查询用户信息")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("queryUserById")
    public Result queryUserById(@RequestParam(name = "userId") Long userId) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.queryUserById(userId);
    }
//
//    @ApiOperation("根据 inviterId 分页查询用户")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="query")
//    @PostMapping("queryUserByInviterId")
//    public Result queryUserByInviterId(@Validated @RequestBody UserQueryByInviterIdReq req) {
//        // 参数校验
////        if (StringUtils.isBlank(req.getName()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
////        if (StringUtils.isBlank(req.getAttrs()))
////            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
//        return userService.queryUserByInviterId(req);
//    }

    @ApiOperation("用户在游戏端登录")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="query")
    @PostMapping("getUserProfile")
    public Result getUserProfile(@RequestParam(name="userName") @NotBlank(message = "user name cannot be blank") String userName,
                                 @RequestParam(name="passswd") @NotBlank(message = "passwd cannot be blank") String passwd) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.getUserProfile(userName,passwd);
    }


    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("updateUser")
    public Result updateUser(@Validated @RequestBody UserUpdateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.updateUser(req);
    }


    @ApiOperation("渠道商注册")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("createChannelLeader")
    public Result createChannelLeader(@RequestParam(name="walletAddress") @NotBlank(message = "wallet address cannot be blank") String walletAddress) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.createChannelLeader( walletAddress);
    }

}

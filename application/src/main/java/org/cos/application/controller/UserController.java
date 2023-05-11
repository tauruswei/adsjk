package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.UserService;
import org.cos.common.entity.data.req.UserCreateReq;
import org.cos.common.entity.data.req.UserSendCodeReq;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "用户controller",tags = "用户组模块", description = "用户组模块 Rest API")
@RequestMapping("user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation("发送验证码")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("send_code")
    public Result sendCode(@Validated @RequestBody UserSendCodeReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.sendCode(req);
    }

    @ApiOperation("用户注册")
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
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
//    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("login")
    public Result login(@Validated @RequestBody UserCreateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.login(req);
    }
}

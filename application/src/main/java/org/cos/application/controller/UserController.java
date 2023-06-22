package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cos.application.service.UserService;
import org.cos.common.entity.data.req.UserCreateReq;
import org.cos.common.entity.data.req.UserLoginReq;
import org.cos.common.entity.data.req.UserSendCodeReq;
import org.cos.common.entity.data.req.UserUpdateReq;
import org.cos.common.exception.GlobalException;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.cos.common.result.Result1;
import org.cos.common.result.TestResult;
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
    @ApiOperation("发送验证码 aws，但是不使用 aws ec2 机器")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("sendCode1")
    public Result sendCode1(@Validated @RequestBody UserSendCodeReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.sendCode1(req);
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
    @ApiOperation("创建游客账户")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("createGuestUser")
    public Result createGuestUser() {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.createGuestUser();
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
    @ApiOperation("用户退出登录")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("logout")
    public Result logout(@Validated @RequestBody UserLoginReq req) {
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

    @ApiOperation("根据用户名 查询用户信息")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("queryUserByName")
    public Result queryUserByName(@RequestParam(name = "name") String name) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.queryUserByName(name);
    }

    @ApiOperation("根据邮箱 查询用户信息")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("queryUserByEmail")
    public Result queryUserByEmail(@RequestParam(name = "email") String email) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.queryUserByEmail(email);
    }

    @ApiOperation("根据邮箱 查询用户是否注册")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("validateEmailIsAvaliable")
    public Result validateEmailIsAvaliable(@RequestParam(name = "email") String email) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.validateEmailIsAvaliable(email);
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




    @ApiOperation("更新用户")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String",paramType="header")
    @PostMapping("updateUser")
    public Result updateUser(@Validated @RequestBody UserUpdateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.updateUser(req);
    }


    @ApiOperation("重置密码")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String",paramType="header")
    @PostMapping("resetPasswd")
    public Result resetPasswd(@Validated @RequestBody UserUpdateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.resetPasswd(req);
    }

    @ApiOperation("修改邮箱")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String",paramType="header")
    @PostMapping("modifyEmail")
    public Result modifyEmail(@Validated @RequestBody UserUpdateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.modifyEmail(req);
    }

    @ApiOperation("修改密码")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "String",paramType="header")
    @PostMapping("modifyPasswd")
    public Result modifyPasswd(@Validated @RequestBody UserUpdateReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.modifyPasswd(req);
    }


    @ApiOperation("渠道商注册")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("createChannelLeader")
    public Result createChannelLeader(@RequestBody UserUpdateReq req) {
        if (StringUtils.isBlank(req.getWalletAddress())){
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("wallet address can not be null"));
        }
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.createChannelLeader(req.getWalletAddress());
    }

    @ApiOperation("根据钱包地址查询渠道商")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("queryChannelLeaderByWalletAddress")
    public Result queryChannelLeaderByWalletAddress(@RequestBody UserUpdateReq req) {
        if (StringUtils.isBlank(req.getWalletAddress())){
            throw new GlobalException(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("wallet address can not be null"));
        }
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.queryChannelLeaderByWalletAddress(req.getWalletAddress());
    }
    @ApiOperation("查询俱乐部老板和渠道商的地址")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "Long",paramType="header")
    @PostMapping("queryClubAndChannelAddress")
    public Result queryClubAndChannelAddress(@RequestParam(name="userId") Long userId) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.queryClubAndChannelAddress(userId);
    }

    @ApiOperation("查询区块链浏览器的地址")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "Long",paramType="header")
    @PostMapping("queryBlockChainExplorer")
    public Result queryBlockChainExplorer(@RequestParam(name="blockChainType") int blockChainType) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        return userService.queryBlockChainExplorer(blockChainType);
    }
    @ApiOperation("test1 ")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("test1")
    public TestResult test1(@Validated @RequestBody UserLoginReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        TestResult testResult =new TestResult();
        testResult.returnCode="success";
        testResult.error="";
        Result1 result1 = new Result1();
        result1.txid = "testtx";
        result1.encode="UTF-8";
        result1.payload="";
        testResult.result=result1;
        return testResult;
    }
    @ApiOperation("test2 ")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("test2")
    public TestResult test2(@Validated @RequestBody UserLoginReq req) {
        // 参数校验
//        if (StringUtils.isBlank(req.getName()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM,"templateName");
//        if (StringUtils.isBlank(req.getAttrs()))
//            return BaseResultEntity.failure(BaseResultEnum.LACK_OF_PARAM, "attrs");
        TestResult testResult =new TestResult();
        testResult.returnCode="failture";
        testResult.error="";
        Result1 result1 = new Result1();
        result1.txid = "testtx";
        result1.encode="UTF-8";
        result1.payload="";
        testResult.result=result1;
        return testResult;
    }






}

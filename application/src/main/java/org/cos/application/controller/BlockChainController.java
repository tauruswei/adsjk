package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.BlockChainService;
import org.cos.application.service.UserService;
import org.cos.common.entity.data.req.UserSendCodeReq;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Api(value = "Block chain controller",tags = "Block chain 模块", description = "Block chain 模块 Rest API")
@RequestMapping("blockChain")
@RestController
@Slf4j
public class BlockChainController {
    @Autowired
    private BlockChainService blockChainService;
    @Autowired
    private Web3j web3j;

    @ApiOperation("查询交易")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String",paramType="header")
    @PostMapping("call")
    public Result call(@Validated @RequestBody UserSendCodeReq req) throws ExecutionException, InterruptedException {
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();

//        return blockChainService.call();
        return Result.success();
    }



}

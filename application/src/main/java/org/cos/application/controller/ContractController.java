package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.common.config.Web3jConfiguration;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Contract controller", tags = "Contract 模块", description = "contract 模块 Rest API")
@RequestMapping("contract")
@RestController
@Slf4j
public class ContractController {
    @Autowired
    private Web3jConfiguration web3jConfiguration;

    @ApiOperation("获取合约配置参数")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "query")
    @PostMapping("getContractConfig")
    public Result getContractConfig(@RequestParam(name = "network",required = true) String network) {
        return Result.success(web3jConfiguration.getNetworkConfig().get(network).getContract());
    }

}

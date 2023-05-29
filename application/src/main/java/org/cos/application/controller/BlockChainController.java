package org.cos.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cos.application.service.BlockChainService;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Api(value = "Block chain controller", tags = "Block chain 模块", description = "Block chain 模块 Rest API")
@RequestMapping("blockChain")
@RestController
@Slf4j
public class BlockChainController {
    @Autowired
    private BlockChainService blockChainService;
    @Autowired
    private Web3j web3j;
    //    @Autowired
//    private Contract contract;
    @Value("${web3j.bsc.contractAddress}")
    private String contractAddress;
    @Value("${web3j.bsc.privateKey}")
    private String privateKey;

    @ApiOperation("查询区块号")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    @GetMapping("getBlockNumber")
    public Result getBlockNumber() throws ExecutionException, InterruptedException, IOException {

        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
        BigInteger blockNumber = ethBlockNumber.getBlockNumber();
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        System.out.println("Client version: " + web3ClientVersion.getWeb3ClientVersion());

        return Result.success(blockNumber.toString());
    }

    @ApiOperation("查询交易详细信息")
    @ApiImplicitParam(name = "Authorization", value = "token", required = false, dataType = "String", paramType = "header")
    @GetMapping("getTransaction")
    public Result getTransaction() throws ExecutionException, InterruptedException, IOException {
        // 交易的hash值，用来查询交易
        String txHash = "0xdaeb0ff1a9c0b76f3e0e33a51a6208c6a1ae55dcc031d2502851991b2f842834";

        // 得到交易信息
        EthTransaction txn = web3j.ethGetTransactionByHash(txHash).send();
        String blockHash = txn.getResult().getBlockHash();
        BigInteger blockNumber = txn.getResult().getBlockNumber();

        // 得到交易收据
        EthGetTransactionReceipt txnReceipt = web3j.ethGetTransactionReceipt(txHash).send();
        TransactionReceipt receipt = txnReceipt.getTransactionReceipt().get();

        // 得到收据中的块时间戳
        BigInteger blockTime = web3j.ethGetBlockByHash(blockHash, true).send().getBlock().getTimestamp();

        // 打印块号、时间和交易信息
        System.out.println("Block number: " + blockNumber);
        System.out.println("Block time: " + blockTime);
        System.out.println("Transaction: " + receipt);

        return Result.success();
    }


}

package org.cos.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
@Transactional

public class BlockChainService {
//    public Result call() throws ExecutionException, InterruptedException {
//
//
//        return Result.success();
//
//
////        Credentials credentials = Credentials.create(new File("yourPrivateKeyFile"));
////        String adminAddress = credentials.getAddress();
////        String adminPrivateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
////
////        TransactionManager txManager = new RawTransactionManager(web3j, credentials);
////        String contractAddress = "0x...";
////        String abi = "....";  // 合约 ABI
////        MyContract myContract = MyContract.load(contractAddress, web3j, txManager, Contract.GAS_PRICE, Contract.GAS_LIMIT);
//    }
//    String CONTRACT_ADDRESS = "0x3F2604138aD3dC1E1c6FD20a68b507995Ef65934";
//    String ABI = "[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"message\",\"type\":\"string\"}],\"name\":\"setMessage\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getMessage\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]";
//    Web3j web3j;
//    AbstractContract contract;
//    public SimpleContractService(Web3j web3j, TransactionManager transactionManager) {
//        this.web3j = web3j;
//        this.contract = AbstractContract.load(CONTRACT_ADDRESS, web3j, transactionManager, AbstractContract.GAS_PRICE, AbstractContract.GAS_LIMIT);
//    }
//    public String getMessage() throws Exception {
//        Function function = new Function("getMessage", Arrays.<Type>asList(), Arrays.<Type>asList(new TypeReference<Utf8String>() {}));
//        String message = contract.getMessage().sendAsync().get().getValue();
//        return message;
//    }
//    public String setMessage(String message) throws Exception {
//        Function function = new Function("setMessage", Arrays.<Type>asList(new Utf8String(message)), Collections.<TypeReference<?>>emptyList());
//        TransactionReceipt tx = contract.setMessage(new Utf8String(message)).sendAsync().get();
//        return tx.getTransactionHash();
//    }
}

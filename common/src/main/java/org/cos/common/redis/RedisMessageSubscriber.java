package org.cos.common.redis;

import com.alibaba.fastjson.JSON;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.PoolUser;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.exception.GlobalException;
import org.cos.common.repository.*;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.Date;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2020/4/19 0019 23:13
 */

@Component
@Transactional
public class RedisMessageSubscriber implements MessageListener {
    private static final Logger log = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    //    @Autowired
//    Contract contract;
    @Autowired
    RedisService redisService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRelationRepository userRelationRepository;
    @Autowired
    TransWebsiteRepository transWebsiteRepository;
    @Autowired
    PoolUserRepository poolUserRepository;
    @Autowired
    NFTRepository nftRepository;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    BaseConfiguration baseConfiguration;

    @Autowired
    private Web3j web3j;
    // todo 网络名称
    @Value("${web3j.networkConfig.bsc.blockNumber}")
    private Long bscBlockNumber;

//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String channel = new String(message.getChannel());
//        String body = new String(message.getBody());
//        log.info("redis message：" + body);
//        // 处理接收到的消息
//        CosdStakeForSLReq cosdStakeForSLReq = JSON.parseObject(body, CosdStakeForSLReq.class);
////         transactionReceipt = null ;
//        try {
//            while (ObjectUtils.isEmpty(cosdStakeForSLReq.getBlockNumber()) || cosdStakeForSLReq.getBlockNumber() == 0L) {
//                Optional<TransactionReceipt> transactionReceipt =web3j.ethGetTransactionReceipt(cosdStakeForSLReq.getTxId()).send().getTransactionReceipt();
//                if (transactionReceipt.isPresent()) {
//                    // 获取交易状态
//                    String status = transactionReceipt.get().getStatus();
//                    if (status.equals("0x1")) {
//                        // 交易成功
//                        System.out.println("Transaction is success.");
//                        cosdStakeForSLReq.setBlockNumber(transactionReceipt.get().getBlockNumber().longValue());
//                    } else {
//                        // todo 交易失败，这种情况交易不能再放到队列里了
//                        System.out.println("Transaction is failed.");
//                        throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR.fillArgs(transactionReceipt.get().getRevertReason()));
//                    }
//                } else {
////                    try {
//                        Thread.sleep(3000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//                    continue;
//                }
//            }
//
//            EthBlockNumber ethBlockNumber = ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
////
//            BigInteger blockNumber = ethBlockNumber.getBlockNumber();
//
//            while (blockNumber.compareTo(BigInteger.valueOf(cosdStakeForSLReq.getBlockNumber() + bscBlockNumber)) < 0) {
//                    Thread.sleep(6000);
//                    blockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
//            }
//
//            Optional<TransactionReceipt> transactionReceipt1 = web3j.ethGetTransactionReceipt(cosdStakeForSLReq.getTxId()).send().getTransactionReceipt();
//                if (transactionReceipt1.isPresent()) {
//                    // 获取交易状态
//                    String status = transactionReceipt1.get().getStatus();
//
//                    if (status.equals("0x1")) {
//                        // 交易成功
//                        System.out.println("Transaction is success.");
//                        cosdStakeForSLReq.setBlockNumber(transactionReceipt1.get().getBlockNumber().longValue());
//                        //                transactionReceipt1.get().
//                        // 获取区块时间戳
//                        EthBlock.Block block = null;
//
//                        while (ObjectUtils.isEmpty(block)) {
////                            try {
//                                block = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(transactionReceipt1.get().getBlockNumber()), true).send().getBlock();
//                                cosdStakeForSLReq.setUpChainTime(block.getTimestamp().longValue());
////                            } catch (Exception e) {
////                                try {
//                                    Thread.sleep(1000);
////                                } catch (InterruptedException e1) {
////                                    e1.printStackTrace();
////                                }
////                                continue;
//                            }
//
//                            //                    long blockTimestamp = block.getTimestamp().longValue();
////                        }
//
//                    } else {
//
//                        System.out.println("Transaction is failed.");
//                        throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR.fillArgs(transactionReceipt1.get().getRevertReason()));
//                    }
////                    break;
//                } else {
////                  // todo 交易失败，这种情况交易不能再放到队列里了
//
////                throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR);
//                }
////            }
//
////            System.out.println(cosdStakeForSLReq.getTxId());
////            System.out.println(blockNumber);
////            System.out.println(cosdStakeForSLReq.getBlockNumber());
////            EthTransaction txn = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();
////
////            System.out.println(1);
////
////            String blockHash = txn.getResult().getBlockHash();
////            System.out.println(2);
////
////            BigInteger blockTime = web3j.ethGetBlockByHash(blockHash, true).send().getBlock().getTimestamp();
////            System.out.println(3);
//
////            cosdStakeForSLReq.setBlockNumber(transactionReceipt1.get().getBlockNumber().longValue());
////            cosdStakeForSLReq.setUpChainTime(transactionReceipt1.get().blockTime.longValue());
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
////        transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.FAIL, 0);
////            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs("监听链上事件异常：" + e.getMessage()));
////        }
//        transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.SUCCESS, cosdStakeForSLReq.getUpChainTime());
//
//        Result result = transaction(cosdStakeForSLReq);
//        if (result.getCode() != 0) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR);
//        }
//    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info(body);
        // 处理接收到的消息
        CosdStakeForSLReq cosdStakeForSLReq = JSON.parseObject(body, CosdStakeForSLReq.class);
        try {
            log.info("1");
            while (ObjectUtils.isEmpty(cosdStakeForSLReq.getBlockNumber()) || cosdStakeForSLReq.getBlockNumber().equals(0L)) {
                log.info("11");
                EthTransaction ethTx = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();
                log.info("12");
                Transaction tx = ethTx.getTransaction().orElse(null);
                if (ObjectUtils.isEmpty(tx)) {
                    log.info("2");

                    Thread.sleep(3000);
                    continue;
                }
                cosdStakeForSLReq.setBlockNumber(tx.getBlockNumber().longValue());
            }
            log.info("3");


            EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
            BigInteger blockNumber = ethBlockNumber.getBlockNumber();
//            BigInteger blockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
            log.info("4");
            while (blockNumber.compareTo(BigInteger.valueOf(cosdStakeForSLReq.getBlockNumber() + bscBlockNumber)) < 0) {
                Thread.sleep(6000);
                log.info("5");
                blockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
            }
            log.info("6");
            EthTransaction txn = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();
            log.info("7");
            String blockHash = txn.getResult().getBlockHash();
            log.info("8");
            BigInteger blockTime = web3j.ethGetBlockByHash(blockHash, true).send().getBlock().getTimestamp();
            log.info("9");
            cosdStakeForSLReq.setBlockNumber(txn.getResult().getBlockNumber().longValue());
            cosdStakeForSLReq.setUpChainTime(blockTime.longValue());

        } catch (Exception e) {
            System.out.println("监听链上事件异常：" + e.getMessage());
            redisService.publish(baseConfiguration.getRedisChannel(), body);
            transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.FAIL, 0);
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs("监听链上事件异常：" + e.getMessage()));
        }
        transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.SUCCESS, cosdStakeForSLReq.getUpChainTime());

        Result result = transaction(cosdStakeForSLReq);
        if (result.getCode() != 0) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR);
        }
    }

    public Result transaction(CosdStakeForSLReq req) {
        switch (req.getTransType()) {
            // 用户质押 COSD 到DEFI
            case 1:
                // 用户质押 COSD 到星光
            case 2:
                // 用户质押 COSD 到 club
            case 3:
                // 用户从 defi 解押 COSD
            case 4:
                // 用户从 星光 解押 COSD
            case 5:
                // 用户从 club 解押 COSD
            case 6:
                PoolUser poolUser1 = poolUserRepository.queryPoolUserByUserIdAndPoolId(req.getPoolId(), req.getFromUserId());
                // 只是在 更新 club 质押池的时候，才会用到 user
                User user = new User();
                if (ObjectUtils.isEmpty(poolUser1)) {
                    PoolUser poolUser = new PoolUser();
                    poolUser.setUserId(req.getFromUserId());
                    poolUser.setPoolId(req.getPoolId());
                    poolUser.setUpdateTime(new Date());
                    poolUser.setCreateTime(new Date());

                    try {
                        poolUser.setAmount(req.getFromAmount());
                        poolUserRepository.insertPoolUser(poolUser);
                    } catch (Exception e) {
                        throw new GlobalException(CodeMsg.POOL_USER_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                    user.setUserType(poolUser.getAmount() >= baseConfiguration.getClubAmount() ? CommonConstant.USER_CLUB : CommonConstant.USER_PLAYER);
                } else {
                    //todo
                    poolUser1.setUpdateTime(new Date());
                    poolUser1.setAmount(poolUser1.getAmount() + req.getFromAmount());
                    if (poolUser1.getAmount() < 0) {
                        throw new GlobalException(CodeMsg.POOL_USER_BALANCE_ERROR);
                    }
                    poolUserRepository.updatePoolUser(poolUser1);
                    user.setUserType(poolUser1.getAmount() >= baseConfiguration.getClubAmount() ? CommonConstant.USER_CLUB : CommonConstant.USER_PLAYER);
                }
                if ((req.getPoolId() == CommonConstant.POOL_CLUB)) {
                    user.setId(req.getFromUserId());
                    User user1 = userRepository.queryUserById(req.getFromUserId());
                    if (user.getUserType()!=user1.getUserType()){
                        user.setUpdateTime(new Date());
                        userRepository.updateUser(user);
                    }
                    // 俱乐部老板的变成普通用户后，需要更新 userrelation表，将俱乐部老板邀请的用户的 level1 和 level0变成空
                    if (user.getUserType()==CommonConstant.USER_PLAYER){
                        userRelationRepository.batchUpdateUserRelationClub(user.getId(),new Date());
                    }
                }
                break;
            // 用户充值购买 EVIC 积分、提现 EVIC 积分、用户购买 COSD
            case 0:
                // 用户购买 EVIC 积分
            case 7:
                Asset asset = new Asset();
                // 查询当前用户的资产
                Asset asset1 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getToAssetType());
                if (ObjectUtils.isEmpty(asset1)) {
//                    throw new GlobalException(CodeMsg.ASSET_NOT_EXIST_ERROR);
                    asset.setUserId(req.getFromUserId());
                    if (req.getFromAssetType() != CommonConstant.USDT) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    if ((req.getToAssetType() != CommonConstant.COSD) && (req.getToAssetType() != CommonConstant.EVIC)) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    asset.setAssetType(req.getToAssetType());
                    asset.setAmount(req.getToAmount());
                    asset.setCreateTime(new Date());
                    asset.setUpdateTime(new Date());
                    asset.setRemark(req.getRemark());
                    try {
                        assetRepository.insertAsset(asset);
                    } catch (Exception e) {
                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
                    }
                } else {
                    asset1.setAmount(asset1.getAmount() + req.getToAmount());
                    if (asset1.getAmount() < 0) {
                        throw new GlobalException(CodeMsg.ASSET_AMOUNT_ERROR);
                    }
                    asset1.setUpdateTime(new Date());
                    if ((req.getToAssetType() != CommonConstant.COSD) && (req.getToAssetType() != CommonConstant.EVIC)) {
                        throw new GlobalException(CodeMsg.ASSET_TYPE_ERROR);
                    }
                    asset1.setAssetType(req.getToAssetType());
                    assetRepository.updateAsset(asset1);
                }
                break;
            // 用户提现 EVIC
            case 8:
//                Asset asset2 = new Asset();
//                // 查询当前用户的资产
//                Asset asset3 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getFromAssetType());
//
//                asset3.setAmount(asset3.getAmount() + req.getFromAmount());
//                if (asset3.getAmount() < 0) {
//                    throw new GlobalException(CodeMsg.ASSET_ACOUNT_ERROR);
//                }
//                asset3.setUpdateTime(new Date());
//                try {
//                    assetRepository.updateAsset(asset3);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_UPDATE_ERROR.fillArgs(e.getMessage()));
//                }
                break;
            // 用户购买 NFT 盲盒
            case 9:
                NFT nft = new NFT();
                if (!ObjectUtils.isEmpty(nftRepository.queryNFTByTokenId(req.getNftVo().getTokenId()))) {
                    throw new GlobalException(CodeMsg.NFT_EXIST_ERROR);
                }
                nft.setUserId(req.getFromUserId());
                nft.setStatus(req.getNftVo().getStatus());
                nft.setTokenId(req.getNftVo().getTokenId());
                nft.setAttr1(req.getNftVo().getAttr1());
                nft.setAttr2(req.getNftVo().getAttr2());
                nft.setTxid(req.getTxId());
                nft.setGameType(req.getNftVo().getGameType());
                nft.setUpchainTime(req.getUpChainTime());
                nft.setCreateTime(new Date());
                nft.setUpdateTime(new Date());
                try {
                    nftRepository.insertNFT(nft);
                } catch (Exception e) {
                    throw new GlobalException(CodeMsg.NFT_ADD_ERROR.fillArgs(e.getMessage()));
                }
                break;
            case 10:
                // todo 用户交易 NFT
                break;
            default:
                // 交易类型错误
                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TYPE_ERROR);
        }
        return Result.success();
    }

}

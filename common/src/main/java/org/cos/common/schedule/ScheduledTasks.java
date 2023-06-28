package org.cos.common.schedule;

import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.PoolUser;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.RedisMessageSubscriber;
import org.cos.common.redis.RedisService;
import org.cos.common.redis.TransactionKey;
import org.cos.common.repository.*;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import redis.clients.jedis.StreamEntryID;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {
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
    @Value("${spring.redis.stream.maxTime}")
    private Long maxTime;

    @Autowired
    private Web3j web3j;
    // todo 网络名称
    @Value("${web3j.networkConfig.bsc.blockNumber}")
    private Long bscBlockNumber;


    private static final Logger log = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    // 每隔2秒执行一次，读取 redis-stream 新提交的消息
     @Scheduled(fixedRate = 2000)
    public void consumeMessage() {
         List<Map<StreamEntryID, CosdStakeForSLReq>> maps = redisService.xreadGroup(TransactionKey.getTx, "", CosdStakeForSLReq.class);
         maps.forEach(map -> {
             map.forEach((id, cosdStakeForSLReq) -> {
                 try {
                     while (ObjectUtils.isEmpty(cosdStakeForSLReq.getBlockNumber()) || cosdStakeForSLReq.getBlockNumber().equals(0L)) {
                         EthTransaction ethTx = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();
                         Transaction tx = ethTx.getTransaction().orElse(null);
                         if (ObjectUtils.isEmpty(tx)) {
                             Thread.sleep(3000);
                             continue;
                         }
                         cosdStakeForSLReq.setBlockNumber(tx.getBlockNumber().longValue());
                     }

                     EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
                     BigInteger blockNumber = ethBlockNumber.getBlockNumber();

                     while (blockNumber.compareTo(BigInteger.valueOf(cosdStakeForSLReq.getBlockNumber() + bscBlockNumber)) < 0) {
                         Thread.sleep(6000);
                         blockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
                     }

                     EthTransaction txn = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();

                     String blockHash = txn.getResult().getBlockHash();

                     BigInteger blockTime = web3j.ethGetBlockByHash(blockHash, true).send().getBlock().getTimestamp();

                     cosdStakeForSLReq.setBlockNumber(txn.getResult().getBlockNumber().longValue());
                     cosdStakeForSLReq.setUpChainTime(blockTime.longValue());

                 } catch (Exception e) {
                     log.error("监听链上事件异常：" + e.getMessage());
                     transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.FAIL, 0);
                 }
                 redisService.xack(id);
                 transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.SUCCESS, cosdStakeForSLReq.getUpChainTime());
                 Result result = transaction(cosdStakeForSLReq);
                 if (result.getCode() != 0) {
                     log.error("更新链下信息失败：msg:{},cosdStakeForSLReq:{}", result.getMsg(), cosdStakeForSLReq);
                 }
             });
         });

    }

    // 每隔5秒执行一次,读取未被确认的交易
    @Scheduled(fixedRate = 5000)
    public void consumePendingMessage() {
        // 一次性读取 10个 pending 消息
        List<Map<StreamEntryID, CosdStakeForSLReq>> maps = redisService.xpending(TransactionKey.getTx, "", CosdStakeForSLReq.class);
        maps.forEach(map -> {
            map.forEach((id, cosdStakeForSLReq) -> {

                // 判断当前消息从创建到现在是否超过 制定的时间（毫秒）
                //获取 StreamEntryID 的时间戳部分，它以毫秒为单位
                long timestampInStreamEntryID = id.getTime();
                //获取当前的 UNIX 时间戳，以毫秒为单位
                long currentTimestamp = System.currentTimeMillis();
                //判断 StreamEntryID 中的时间戳是否比当前时间戳小24小时（86400000毫秒）
                if (currentTimestamp - timestampInStreamEntryID > maxTime) {
                    redisService.xack(id);
                    return;
                }

                try {
                    while (ObjectUtils.isEmpty(cosdStakeForSLReq.getBlockNumber()) || cosdStakeForSLReq.getBlockNumber().equals(0L)) {
                        EthTransaction ethTx = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();
                        Transaction tx = ethTx.getTransaction().orElse(null);
                        if (ObjectUtils.isEmpty(tx)) {
                            Thread.sleep(3000);
                            continue;
                        }
                        cosdStakeForSLReq.setBlockNumber(tx.getBlockNumber().longValue());
                    }

                    EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
                    BigInteger blockNumber = ethBlockNumber.getBlockNumber();
                    //            BigInteger blockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();

                    while (blockNumber.compareTo(BigInteger.valueOf(cosdStakeForSLReq.getBlockNumber() + bscBlockNumber)) < 0) {
                        Thread.sleep(6000);
                        blockNumber = web3j.ethBlockNumber().sendAsync().get().getBlockNumber();
                    }

                    EthTransaction txn = web3j.ethGetTransactionByHash(cosdStakeForSLReq.getTxId()).send();

                    String blockHash = txn.getResult().getBlockHash();

                    BigInteger blockTime = web3j.ethGetBlockByHash(blockHash, true).send().getBlock().getTimestamp();

                    cosdStakeForSLReq.setBlockNumber(txn.getResult().getBlockNumber().longValue());
                    cosdStakeForSLReq.setUpChainTime(blockTime.longValue());

                } catch (Exception e) {
                    //                System.out.println("监听链上事件异常：" + e.getMessage());
                    log.error("监听链上事件异常：" + e.getMessage());
                    //                        redisService.publish(baseConfiguration.getRedisChannel(), body);
                    transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.FAIL, 0);
                    //                throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs("监听链上事件异常：" + e.getMessage()));
                }
                redisService.xack(id);
                transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.SUCCESS, cosdStakeForSLReq.getUpChainTime());

                Result result = transaction(cosdStakeForSLReq);
                if (result.getCode() != 0) {
                    log.error("更新链下信息失败：msg:{},cosdStakeForSLReq:{}", result.getMsg(), cosdStakeForSLReq);
                }
            });
        });

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

package org.cos.common.schedule;

import org.apache.commons.lang3.ObjectUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.dto.UserRelationDTO;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.NFT;
import org.cos.common.entity.data.po.PoolUser;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.entity.data.vo.NFTVo;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.*;
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

    public static String luna_script = "if redis.call('exists', KEYS[1]) == 1 then " +
            "local nft = redis.call('get', KEYS[1]); " +
            "local nftJson = cjson.decode(nft); " +
            "nftJson['status'] = ARGV[1]; " +
            "redis.call('set', KEYS[1], cjson.encode(nftJson)); " +
            "return 1; " +
            "end; " +
            "return 0;";


    private static final Logger log = LoggerFactory.getLogger(RedisMessageSubscriber.class);

    // 每隔5秒执行一次，读取 redis-stream 新提交的消息
    @Scheduled(fixedRate = 10000)
    public void consumeMessage() {
        List<Map<StreamEntryID, CosdStakeForSLReq>> maps = redisService.xreadGroup(TransactionKey.getTx, "", CosdStakeForSLReq.class);
        maps.forEach(map -> {

            if (ObjectUtils.isEmpty(map)) {
                return;
            }
//            log.info("读取 redis 中新提交的交易");

            map.forEach((id, cosdStakeForSLReq) -> {
                log.info("读取 redis 中新提交的交易，txid = {}", cosdStakeForSLReq.getTxId());
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
                    throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR);
//                    transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.FAIL, 0);
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

    // 每隔10秒执行一次,读取未被确认的交易
    @Scheduled(fixedRate = 30000)
    public void consumePendingMessage() {
        // 一次性读取 10个 pending 消息
        List<Map<StreamEntryID, CosdStakeForSLReq>> maps = redisService.xpending(TransactionKey.getTx, "", CosdStakeForSLReq.class);

        maps.forEach(map -> {

            if (ObjectUtils.isEmpty(map)) {
                return;
            }
//            log.info("读取 redis 中未被确认的交易");

            map.forEach((id, cosdStakeForSLReq) -> {

                log.info("读取 redis 中未被确认的交易，txid = {}", cosdStakeForSLReq.getTxId());

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
                    throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR);

                    //                        redisService.publish(baseConfiguration.getRedisChannel(), body);
//                    transWebsiteRepository.updateTransWebsiteStatus(cosdStakeForSLReq.getTransWebsiteId(), CommonConstant.FAIL, 0);
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
                    if (user.getUserType() != user1.getUserType()) {
                        user.setUpdateTime(new Date());
                        userRepository.updateUser(user);
                    }
                    // 俱乐部老板的变成普通用户后，需要更新 userrelation表，将俱乐部老板邀请的用户的 level1 和 level0变成空
                    if (user.getUserType() == CommonConstant.USER_PLAYER) {
                        userRelationRepository.batchUpdateUserRelationClub(user.getId(), new Date());
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
                // 删除 evic 提现的过期 key

                redisService.del(EvicKey.getWithdrawKey.getPrefix() + String.format("%s:%s", req.getFromUserId().toString(), req.getFromAmount().toString()));
                log.info("删除过期 key{}", EvicKey.getWithdrawKey.getPrefix() + String.format("%s:%s", req.getFromUserId().toString(), req.getFromAmount().toString()));
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
                // 从redis中获取最新的 NFTvo 对象，因为在 active 列表选择 use it for game，会更新 NFTVo 对象，然后删除 nft 在redis 中的key
                String luaScript = "local nft = redis.call('GET', KEYS[1]) \n" +
                        "redis.call('DEL', KEYS[1]) \n" +
                        "return nft";
                NFTVo nftVo = redisService.evalGet(NFTKey.getTokenId, req.getNftVo().getTokenId(), luaScript, NFTVo.class);
                if (ObjectUtils.isNotEmpty(nftVo)) {
                    req.setNftVo(nftVo);
                }
                NFT nft = new NFT();
                if (!ObjectUtils.isEmpty(nftRepository.queryNFTByTokenId(req.getNftVo().getTokenId()))) {
                    throw new GlobalException(CodeMsg.NFT_EXIST_ERROR);
                }

                // 统计 NFT 利润
                User user1 = userRepository.queryUserById(req.getFromUserId());
                UserRelationDTO userRelationDTO = userRelationRepository.queryUserByRelationId(user1.getUserRelationId());
                Double clubProfile = 0D;
                Double channelProfile = 0D;
                if (ObjectUtils.isNotEmpty(userRelationDTO)) {
                    User level0 = userRelationDTO.getLevel0();
                    User level1 = userRelationDTO.getLevel1();
                    if ((!ObjectUtils.isEmpty(level0)) && (!ObjectUtils.isEmpty(level1))) {
                        if (level0.getStatus() == CommonConstant.USER_ACTIVE) {
                            channelProfile = req.getFromAmount() * 0.01;
                        }
                        if (level1.getStatus() == CommonConstant.USER_ACTIVE) {
                            clubProfile = req.getFromAmount() * 0.05;
                        }
                    } else if ((ObjectUtils.isEmpty(level0)) && (!ObjectUtils.isEmpty(level1))) {
                        clubProfile = req.getFromAmount() * 0.05;
                    } else if ((!ObjectUtils.isEmpty(level0)) && (ObjectUtils.isEmpty(level1))) {
                        channelProfile = req.getFromAmount() * 0.05;
                    }
                }
                redisService.incrFloat(ProfileKey.getClub, "", clubProfile);
                redisService.incrFloat(ProfileKey.getChannel, "", channelProfile);
                redisService.incrFloat(ProfileKey.getCompany, "", req.getFromAmount() * 0.1 - clubProfile - channelProfile);
                nft.setUserId(req.getFromUserId());
                if (CommonConstant.NFT_PURCHASED == req.getNftVo().getStatus()) {
                    break;
                }
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
                Boolean exist = redisService.evalSet(NFTKey.getTokenId, req.getNftVo().getTokenId(), luna_script, CommonConstant.NFT_USED);
                if (exist){
                    //如果存在说明购买的 nft 还没有入库，还在redis中排队，因此只需改下状态就行
                    break;
                }else {
                    // 否则直接更新库
                    nftRepository.NFTIntoGame(CommonConstant.NFT_USED,req.getNftVo().getTokenId());
                }
                break;
            default:
                // 交易类型错误
                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TYPE_ERROR);
        }
        return Result.success();
    }


}

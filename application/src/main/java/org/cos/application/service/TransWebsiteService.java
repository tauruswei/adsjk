package org.cos.application.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.cos.common.config.BaseConfiguration;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.dto.PoolUserTimeDTO;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.Pool;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.req.AssetQueryReq;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.entity.data.req.PoolListReq;
import org.cos.common.entity.data.vo.WebNFTVo;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.PolicyKey;
import org.cos.common.redis.RedisService;
import org.cos.common.repository.*;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class TransWebsiteService {
    @Autowired
    private BaseConfiguration baseConfiguration;
    @Autowired
    private TransWebsiteRepository transWebsiteRepository;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private PoolRepository poolRepository;
    @Autowired
    private PoolUserRepository poolUserRepository;
    @Autowired
    private NFTRepository nftRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisService redisService;
    @Value("${web3j.bsc.privateKey}")
    private String privateKey;
    @Value("${web3j.bsc.usdtContractAddress}")
    private String usdtContractAddress;
    @Autowired
    private Web3j web3j;

    public Result transactionAsync(CosdStakeForSLReq req) {
        try {

            if (org.springframework.util.ObjectUtils.isEmpty(userRepository.queryUserById(req.getFromUserId()))) {
                throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
            }
            if (org.springframework.util.ObjectUtils.isEmpty(userRepository.queryUserById(req.getToUserId()))) {
                throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
            }
            TransWebsite transWebsite = new TransWebsite();
            if (!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))) {
                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
            }
            transWebsite.setTxId(req.getTxId());
            transWebsite.setTransType(req.getTransType());
            transWebsite.setFromUserId(req.getFromUserId());
            transWebsite.setFromAssetType(req.getFromAssetType());
            transWebsite.setFromAmount(req.getFromAmount());
            transWebsite.setToUserId(req.getToUserId());
            transWebsite.setToAssetType(req.getToAssetType());
            transWebsite.setToAmount(req.getToAmount());
            transWebsite.setNftTokenId(req.getNftVo().getTokenId());
            transWebsite.setCreateTime(new Date());
            transWebsite.setUpdateTime(new Date());
            transWebsite.setRemark(req.getRemark());
            try {
                transWebsiteRepository.insertTransWebsite(transWebsite);
            } catch (Exception e) {
                throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
            }
            req.setTransWebsiteId(transWebsite.getId());
            redisService.publish(baseConfiguration.getRedisChannel(), JSON.toJSON(req).toString());
        } catch (Exception e) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
        }
        return Result.success();
    }

    public Result withdrawEvic(CosdStakeForSLReq req) {

        User user1 = userRepository.queryUserById(req.getFromUserId());
        if (ObjectUtils.isEmpty(user1)) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST_ERROR);
        }
        AssetQueryReq assetQueryReq = new AssetQueryReq();
        assetQueryReq.setUserId(req.getFromUserId());
        assetQueryReq.setAssetType(CommonConstant.EVIC);
        Result<Asset> result = assetService.queryUserAsset(assetQueryReq);

        if (result.getData().getAmount() + req.getFromAmount() < 0) {
            throw new GlobalException(CodeMsg.ASSET_WITHDRAW_ERROR.fillArgs("用户没有足够的 EVIC 用于提现"));
        }

//        try {
        Credentials credentials = Credentials.create(privateKey);

        // 创建一个值为 1e18 的 uint256 变量
        BigInteger uint256 = BigInteger.valueOf(10).pow(18);

        // 创建一个小数
        BigDecimal decimal = new BigDecimal(Math.abs(req.getFromAmount()) / CommonConstant.USDT_EVIC_EXCHANGE_RATE);

        // 将小数乘以 uint256
        BigDecimal result1 = decimal.multiply(new BigDecimal(uint256));

        // 四舍五入到 3 位小数
        BigDecimal roundedResult = result1.setScale(3, RoundingMode.HALF_UP);

        // 创建函数调用的参数列表
        List<Type> inputParameters = Arrays.asList(
                new Address(user1.getWalletAddress()),
//                new Uint256(BigInteger.valueOf(10).pow(18).multiply(10.8))
                new Uint256(roundedResult.toBigInteger())
        );

        Function function = new Function(
                "transfer",  // function we're calling
                inputParameters,  // Parameters to pass as Solidity Types
                Collections.emptyList());

        String encodedFunction = FunctionEncoder.encode(function);

        // 获取nonce
        BigInteger nonce = null;
        try {
            nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                    .send().getTransactionCount();
        } catch (IOException e) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR.fillArgs(e.getMessage()));
        }

        BigInteger gasPrice = BigInteger.valueOf(20_000_000_0000L);
        BigInteger gasLimit = BigInteger.valueOf(4_300_000);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                usdtContractAddress,
                encodedFunction
        );
        // 使用 TransactionEncoder 将原始交易对象进行签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, 97L, credentials);

        // 将签名后的交易对象发布到区块链上
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction response = null;
        try {
            response = web3j.ethSendRawTransaction(hexValue).send();
        } catch (IOException e) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_UPCHAIN_ERROR.fillArgs(e.getMessage()));
        }
        System.out.println(JSON.toJSONString(response));
        if (ObjectUtils.isNotEmpty(response.getError())) {
            throw new GlobalException(CodeMsg.TRANS_WEBSITE_WITHDRAW_ERROR.fillArgs(response.getError().getMessage()));
        }
//        if (StringUtils.isBlank(response.getTransactionHash())) {
//            log.error(response.getError().toString());
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_WITHDRAW_ERROR.fillArgs(response.getError().toString()));
//        }

        String transactionHash = response.getTransactionHash();
        req.setTxId(transactionHash);

//        更新用户资产的数据库
        result.getData().setAmount(result.getData().getAmount() + req.getFromAmount());
        result.getData().setUpdateTime(new Date());
        assetRepository.updateAsset(result.getData());

        //todo 监听 trans-website 数据库异常上链记录

//            EthTransaction ethTx = web3j.ethGetTransactionByHash(transactionHash).send();
//
//            Transaction tx = ethTx.getTransaction().orElse(null);
//
//            if (tx != null) {
//                System.out.println("Transaction block number: " + tx.getBlockNumber());
//                req.setBlockNumber(tx.getBlockNumber().longValue());
//                req.setTxId(transactionHash);
//            } else {
//                System.out.println("Transaction not found.");
//                throw new GlobalException(CodeMsg.TRANS_WEBSITE_WITHDRAW_ERROR.fillArgs("交易上链失败"));
//            }
//        }catch (Exception e){
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_WITHDRAW_ERROR.fillArgs(e.getMessage().toString()));
//        }
        return transactionAsync(req);
    }

    // 查看用户是否可以解押
    public Result queryUserIsAbleForUnStake(Long userId, Long poolId) {
        PoolUserTimeDTO poolUserTimeDTO = poolUserRepository.queryPoolUserByUserIdForTime(poolId, userId);
        long timestamp = System.currentTimeMillis() / 1000;
        if(ObjectUtils.isEmpty(poolUserTimeDTO)){
            throw new GlobalException(CodeMsg.POOL_USER_NOT_EXIST_ERROR);
        }
        Long createTime = (poolUserTimeDTO.getPoolUserCreateTime());
        // 星光 质押池没有锁仓
        if (poolUserTimeDTO.getPoolType()==CommonConstant.POOL_SL) {
            poolUserTimeDTO.setFlag(true);
        // defi 质押池有一个固定的deadline
        }else if (poolUserTimeDTO.getPoolType() == (CommonConstant.POOL_DEFI)) {
            if (timestamp >= poolUserTimeDTO.getPoolStartTime() + poolUserTimeDTO.getLockTime()) {
                poolUserTimeDTO.setFlag(true);
            } else {
                poolUserTimeDTO.setFlag(false);
            }
        // 俱乐部质押池 锁仓90天，用户首次质押，需要质押90天
        }else if(poolUserTimeDTO.getPoolType() == CommonConstant.POOL_CLUB){
            if (timestamp >= (createTime + poolUserTimeDTO.getLockTime())) {
                poolUserTimeDTO.setFlag(true);
            } else {
                poolUserTimeDTO.setFlag(false);
            }
        }else{
            throw new GlobalException(CodeMsg.POOL_TYPE_ERROR);
        }

        return Result.success(poolUserTimeDTO);

    }

    // 查看用户是否可以质押
    public Result queryUserIsAbleForStake(Long poolId) {
        Pool pool = poolRepository.queryPoolById(poolId);
        if (ObjectUtils.isEmpty(pool)) {
            throw new GlobalException(CodeMsg.POOL_NOT_EXIST_ERROR);
        }
        long timestamp = System.currentTimeMillis() / 1000;
        if (ObjectUtils.isEmpty(pool.getLockTime())) {
            if (timestamp >= pool.getStartTime()) {
                return Result.success(true);
            } else {
                return Result.success(false);
            }
        }
        if (timestamp > (pool.getLockTime() + pool.getStartTime())) {
            return Result.success(false);
        }
        return Result.success(true);
    }

//    // 用户购买 COSD，数据库只是记录，不会修改用户的资产
//    public Result purchaseCOSD(CosdStakeForSLReq req) {
//        // trans_type 0
//        // todo 缺少对用户id
//        TransWebsite transWebsite = new TransWebsite();
//        if (!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
//        }
//        transWebsite.setTxid(req.getTxId());
//        transWebsite.setTransType(r.getTransType());
//        transWebsite.setFromUserId(req.getFromUserId());
//        transWebsite.setFromAssetType(req.getFromAssetType());
//        transWebsite.setFromAmount(req.getFromAmount());
//        transWebsite.setToUserId(req.getToUserId());
//        transWebsite.setToAssetType(req.getToAssetType());
//        transWebsite.setToAmount(req.getToAmount());
//        transWebsite.setCreateTime(new Date());
//        transWebsite.setUpdateTime(new Date());
//        transWebsite.setRemark(req.getRemark());
//        try {
//            transWebsiteRepository.insertTransWebsite(transWebsite);
//        } catch (Exception e) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
//        }
//        return Result.success();
//    }

//    public Result unStakeForSL(CosdStakeForSLReq req) {
//        // trans_type
//        // todo 缺少对用户id
//        TransWebsite transWebsite = new TransWebsite();
//        if (!Objects.isNull(transWebsiteRepository.queryTransWebsiteByTxId(req.getTxId()))) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_TX_EXIST_ERROR);
//        }
//        transWebsite.setTxid(req.getTxId());
//        transWebsite.setTransType(req.getTransType());
//        transWebsite.setFromUserId(req.getFromUserId());
//        transWebsite.setFromAssetType(req.getFromAssetType());
//        transWebsite.setFromAmount(req.getFromAmount());
//        transWebsite.setToUserId(req.getToUserId());
//        transWebsite.setToAssetType(req.getToAssetType());
//        transWebsite.setToAmount(req.getToAmount());
//        transWebsite.setCreateTime(new Date());
//        transWebsite.setUpdateTime(new Date());
//        transWebsite.setRemark(req.getRemark());
//        try {
//            transWebsiteRepository.insertTransWebsite(transWebsite);
//        } catch (Exception e) {
//            throw new GlobalException(CodeMsg.TRANS_WEBSITE_ADD_ERROR.fillArgs(e.getMessage()));
//        }
//        Asset asset = new Asset();
//
//        switch (req.getTransType()) {
//
//
//            // 用户质押 COSD 到星光
//            case 2:
//                if (baseConfiguration.getSlAmount() >= req.getFromAmount()) {
//                    asset.setUserId(req.getFromUserId());
//                    asset.setAssetType(4);
//                    asset.setAssetStatus(2);
//                    asset.setCreateTime(new Date());
//                    asset.setUpdateTime(new Date());
//                    asset.setRemark(req.getRemark());
//                    try {
//                        assetRepository.insertAsset(asset);
//                    } catch (Exception e) {
//                        throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                    }
//                }
//                break;
//            // 用户从星光池中解押 COSD
//            case 5:
//                // todo 后面引入 web3j，从链上查询
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(4);
//                // todo 此处的状态设为1000，是为了与其他状态区分，标志用户失效
//                asset.setAssetStatus(1000);
//                asset.setCreateTime(new Date());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.updateAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            // 用户充值购买 EVIC 积分
//            case 7:
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(3);
//                asset.setAmount(req.getFromAmount() * 100);
//                asset.setCreateTime(new Date());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.insertAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            // 用户提现 EVIC
//            case 8:
//                // 查询当前用户的资产
//                Asset asset1 = assetRepository.queryAssetByUserIdAndType(req.getFromUserId(), req.getFromAssetType());
//                if (req.getFromAmount() > asset1.getAmount()) {
//                    throw new GlobalException(CodeMsg.ASSET_WITHDRAW_ERROR);
//                }
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(3);
//                asset.setAmount(asset1.getAmount() - req.getFromAmount());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.updateAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_UPDATE_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            // 用户购买 NFT 盲盒
//            case 9:
//                asset.setUserId(req.getFromUserId());
//                asset.setAssetType(2);
//                asset.setCreateTime(new Date());
//                asset.setUpdateTime(new Date());
//                asset.setRemark(req.getRemark());
//                try {
//                    assetRepository.insertAsset(asset);
//                } catch (Exception e) {
//                    throw new GlobalException(CodeMsg.ASSET_ADD_ERROR.fillArgs(e.getMessage()));
//                }
//                break;
//            case 10:
//                // todo 用户交易 NFT
//                break;
//            default:
//                // 交易类型错误
//                throw new GlobalException(CodeMsg.TRANS_WEBSITE_TYPE_ERROR);
//        }
//        return Result.success();
//    }


}

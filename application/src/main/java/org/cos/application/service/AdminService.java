package org.cos.application.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.cos.common.constant.CommonConstant;
import org.cos.common.entity.data.dto.AssetDTO;
import org.cos.common.exception.GlobalException;
import org.cos.common.redis.RedisService;
import org.cos.common.repository.NFTRepository;
import org.cos.common.repository.PoolUserRepository;
import org.cos.common.repository.TransWebsiteRepository;
import org.cos.common.repository.UserRepository;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Struct;

@Service
@Slf4j
public class AdminService {
    @Autowired
    private TransWebsiteRepository transWebsiteRepository;
    @Autowired
    private NFTRepository nftRepository;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PoolUserRepository poolUserRepository;

    public Result sumEvicSales(int transType,int days,Long userId){
        BeanUtilsBean beanUtils = new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if(value == null)return;
                super.copyProperty(dest, name, value);
            }
        };
        AssetDTO assetDTO = transWebsiteRepository.purchaseSumEvic(CommonConstant.PURCHASE_EVIC, days, userId);
        AssetDTO assetDTO1 = transWebsiteRepository.withdrawSumEvic(CommonConstant.WITHDRAW_EVIC, days, userId);
        try {
            beanUtils.copyProperties(assetDTO,assetDTO1);
        } catch (Exception e) {
            throw  new GlobalException(CodeMsg.EVIC_STATISTICAL_ERROR);
        }
        return Result.success(assetDTO);
    }


    public Result sumEvicSalesDay(int transType,int days,Long userId){
        BeanUtilsBean beanUtils = new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if(value == null)return;
                super.copyProperty(dest, name, value);
            }
        };
        AssetDTO assetDTO = transWebsiteRepository.purchaseSumEvicDay(CommonConstant.PURCHASE_EVIC, days, userId);

        AssetDTO assetDTO1 = transWebsiteRepository.withdrawSumEvicDay(CommonConstant.WITHDRAW_EVIC, days, userId);
        try {
            beanUtils.copyProperties(assetDTO,assetDTO1);
        } catch (Exception e) {
            throw  new GlobalException(CodeMsg.EVIC_STATISTICAL_ERROR);
        }
        return Result.success(assetDTO);
    }
    public Result statisticalSL(Long userId){
        return Result.success(poolUserRepository.statisticalSL(userId));
    }
    public Result statisticalNFT(Long userId){
        BeanUtilsBean beanUtils = new BeanUtilsBean() {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if(value == null)return;
                super.copyProperty(dest, name, value);
            }
        };
        Long purchased = nftRepository.statisticalNFT(CommonConstant.NFT_PURCHASED);
        Long using = nftRepository.statisticalNFT(CommonConstant.NFT_USED);
        Long used = nftRepository.statisticalNFT(CommonConstant.NFT_INEFFECTIVE);
        @Data
        class NFT{
            Long purchased;
            Long using;
            Long used;
        }
        NFT nft = new NFT();
        nft.setPurchased(purchased);
        nft.setUsing(using);
        nft.setUsed(used);
        return Result.success(nft);
    }

}

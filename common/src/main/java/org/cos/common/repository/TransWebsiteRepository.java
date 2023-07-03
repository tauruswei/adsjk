package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cos.common.entity.data.dto.AssetDTO;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.req.TranBlurListReq;

import java.util.List;

@Mapper
public interface TransWebsiteRepository {
    void insertTransWebsite(TransWebsite transWebsite);
    void updateUser(User user);
    TransWebsite queryUserByEmail(String email);
//    User queryUserByName(String name);
//    List<User> queryUserByInviterId(Integer inviterId);
    TransWebsite queryTransWebsiteByTxId(@Param("txId") String txId);
//    User queryUserByWalletAddressAndUserType(@Param("walletAddress") String walletAddress, @Param("userType") int userType);
    void updateTransWebsiteStatus(@Param("id") Long id, @Param("status") int status,@Param("upchainTime") long time);
    List<TransWebsite> queryTransactionsList(TransWebsite transWebsite);
    List<TransWebsite> queryBlurTransactionsList(@Param("req") TranBlurListReq tranBlurListReq);

    AssetDTO purchaseSumEvic(@Param("transType") int transType, @Param("days") int days, @Param("userId") long userId);
    AssetDTO withdrawSumEvic(@Param("transType") int transType,@Param("days") int days,@Param("userId") long userId);
    AssetDTO purchaseSumEvicDay(@Param("transType") int transType,@Param("days") int days,@Param("userId") long userId);
    AssetDTO withdrawSumEvicDay(@Param("transType") int transType,@Param("days") int days,@Param("userId") long userId);

//    User queryUserByWalletAddressAndUserType(String walletAddress,  int userType);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

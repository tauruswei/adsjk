package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.po.User;

import java.util.List;

@Mapper
public interface TransWebsiteRepository {
    void insertTransWebsite(TransWebsite transWebsite);
    void updateUser(User user);
    TransWebsite queryUserByEmail(String email);
    User queryUserByName(String name);
    List<User> queryUserByInviterId(Integer inviterId);
    TransWebsite queryTransWebsiteByTxId(String txid);
    User queryUserByWalletAddressAndUserType(@Param("walletAddress") String walletAddress, @Param("userType") int userType);
//    User queryUserByWalletAddressAndUserType(String walletAddress,  int userType);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

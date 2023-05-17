package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cos.common.entity.data.po.Asset;
import org.cos.common.entity.data.po.TransWebsite;
import org.cos.common.entity.data.po.User;

import java.util.List;

@Mapper
public interface AssetRepository {
    void insertAsset(Asset asset);
    void updateAsset(Asset asset);
    Asset queryAssetByUserIdAndType(@Param("userId") Long userId,@Param("assetType")int assetType);
    User queryUserByEmail(String email);
    User queryUserByName(String name);
    List<User> queryUserByInviterId(Integer inviterId);
    User queryUserById(Long userId);
//    User queryUserByWalletAddressAndUserType(@Param("walletAddress") String walletAddress, @Param("userType") int userType);
//    User queryUserByWalletAddressAndUserType(String walletAddress,  int userType);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

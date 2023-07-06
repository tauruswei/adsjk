package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.vo.WebStatisticalDataVo;

import java.util.List;

@Mapper
public interface UserRepository {
    void insertUser(User user);
    void updateUser(User user);
    User queryUserByEmail(String email);
    User queryUserByName(String name);
    List<User> queryUserByInviterId(Integer inviterId);
    List<User> queryUserList(int userType);
    User queryUserById(Long userId);
    User queryUserByWalletAddressAndUserType(@Param("walletAddress") String walletAddress, @Param("userType") int userType);
    User queryUserByWalletAddress(@Param("walletAddress") String walletAddress);
    WebStatisticalDataVo countWeb2AndWeb3User();

//    User queryUserByWalletAddressAndUserType(String walletAddress,  int userType);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

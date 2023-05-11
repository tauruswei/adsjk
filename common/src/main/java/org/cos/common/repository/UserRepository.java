package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cos.common.entity.User;
import org.cos.common.entity.sys.po.SysUr;
import org.cos.common.entity.sys.po.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRepository {
    void insertUser(User user);
    User queryUserByEmail(String email);
    void updateSysUserExplicit(Map paramMap);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.cos.common.entity.data.dto.UserRelationDTO;
import org.cos.common.entity.data.po.User;
import org.cos.common.entity.data.po.UserRelation;

import java.util.List;

@Mapper
public interface UserRelationRepository {
    void insertUserRelation(UserRelation userRelation);
    void updateUserRelation(UserRelation userRelation);
    UserRelation queryUserRelationByUserId(Long userId);
    UserRelation queryUserRelationById(String id);
    UserRelationDTO queryUserByRelationId(String id);
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.cos.common.entity.data.po.Pool;

@Mapper
public interface PoolRepository {
    void insertPool(Pool pool);
//    public Pool queryUserAmount
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cos.common.entity.data.po.Pool;

import java.util.List;

@Mapper
public interface PoolRepository {
    void insertPool(Pool pool);
    List<Pool> queryPoolList(@Param("startTime") Long startTime,@Param("lockTime")Long lockTime);
    Pool queryPoolById(@Param("poolId")Long poolId);
//    public Pool queryUserAmount
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
//
//    void updateUserAccount(@Param("userAccount") String userAccount,@Param("userId") Long userId);
}

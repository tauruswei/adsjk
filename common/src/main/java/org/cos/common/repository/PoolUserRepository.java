package org.cos.common.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cos.common.entity.data.dto.PoolUserTimeDTO;
import org.cos.common.entity.data.po.Pool;
import org.cos.common.entity.data.po.PoolUser;

import java.util.List;

@Mapper
public interface PoolUserRepository {
    void insertPoolUser(PoolUser poolUser);
    void updatePoolUser(PoolUser poolUser);
    PoolUser queryPoolUserByUserIdAndPoolId(@Param("poolId") Long poolId,@Param("userId") Long userId);
    PoolUserTimeDTO queryPoolUserByUserIdForTime(@Param("poolId") Long poolId,@Param("userId") Long userId);
//    public Pool queryUserAmount
//    void insertSysUrBatch(@Param("urList") List<SysUr> urList);
//    void deleteSysUrBatch(/*@Param("roleArray") Long[] roleArray,*/ @Param("userId")Long userId);
//    void updateSysUserDelStatus(@Param("userId")Long userId);
    @Select("select count(*) from pool_user where pool_id =2 and amount >=400")
    Long statisticalSL(@Param("userId") Long userId);
}

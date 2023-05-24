package org.cos.common.entity.data.dto;

import lombok.Data;
import org.cos.common.entity.data.po.User;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class PoolUserTimeDTO {
    private Long userId;
    private Long poolId;
    private int poolType;
    private Double amount;
    // 池子开始时间
    private Long poolStartTime;
    // 用户首次质押时间
    private Long poolUserCreateTime;
    // 池子锁仓时间
    private Long lockTime;
    private boolean flag;
}
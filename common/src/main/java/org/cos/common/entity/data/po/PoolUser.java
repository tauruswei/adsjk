package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

@Data
public class PoolUser {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 池子id
     */
    private Long poolId;
    /**
     * 数量，可以是负数
     */
    private double amount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
}

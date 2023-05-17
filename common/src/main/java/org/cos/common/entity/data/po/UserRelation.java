package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户关系表
 */
@Data
public class UserRelation {
    /**
     * 用户id
     */
    private String id;
    /**
     * 渠道商用户 id
     */
    private Long level0;
    /**
     * 俱乐部老板 id
     */
    private Long level1;
    /**
     * 普通用户 id
     */
    private Long level2;
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

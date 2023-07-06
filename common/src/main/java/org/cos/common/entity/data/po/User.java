package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户基础信息表
 */
@Data
public class User {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户类型：0-渠道商；1-俱乐部老板；2-普通用户
     */
    private int userType;
    /**
     * 用户状态：0-可用；1-不可用
     */
    private int status;
    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 用户关系表主键
     */
    private String userRelationId;
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

package org.cos.common.entity;

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
     * 钱包地址
     */
    private String walletAddress;
    /**
     * 邀请人id
     */
    private Integer inviterId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}

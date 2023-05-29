package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

/**
 * NFT 表
 */
@Data
public class NFT {
    /**
     * NFT 表 主键id
     */
    private Long id;
    /**
     * 用户 表 主键id
     */
    private Long userId;
    /**
     * 交易id
     */
    private String txid;
    /**
     * NFT token_id
     */
    private String tokenId;

    /**
     * 游戏类型：0-自走棋
     */
    private int gameType;
    /**
     * NFT 状态 0-已购买；1-已使用；2-已失效
     */
    private int status;
    /**
     * 属性1
     */
    private String attr1;
    /**
     * 属性2
     */
    private String attr2;

    private long upchainTime;
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

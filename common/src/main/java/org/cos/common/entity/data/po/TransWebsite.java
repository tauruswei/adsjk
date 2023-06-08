package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户基础信息表
 */
@Data
public class TransWebsite {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 链上交易 id
     */
    private String txId;

    /**
     * 交易类型:0-用户使用 USDT 购买 COSD、1-用户质押COSD 到 DEFI、2-用户质押 COSD到星光、3-质押 COSD 到俱乐部老板质押池、4-用户从 defi 提现 COSD、5-用户从 星光池中 提现 COSD、6-用户从 俱乐部老板质押池中 提现 COSD、7-用户使用USDT 购买 EVIC、8-用户提现EVIC、9-用户使用USDT 购买 NFT 盲盒、10-NFT交易
     */
    private int transType;
    /**
     * 用户主键id
     */
    private Long fromUserId;
    /**
     * 资产类型
     */
    private int fromAssetType;

    /**
     * 数量
     */
    private double fromAmount;

    /**
     * 用户主键id
     */
    private Long toUserId;
    /**
     * 资产类型
     */
    private int toAssetType;

    /**
     * 数量
     */
    private double toAmount;

    /**
     * 数量
     */
    private String nftTokenId;

    /**
     * 状态
     */
    private int status;

    // 上链时间
    private Long upchainTime;

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

package org.cos.common.entity.data.po;

import lombok.Data;

import java.util.Date;

/**
 * 用户基础信息表
 */
@Data
public class Asset {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 资产类型 0-USDT；1-COSD；2-NFT；3-EVIC；4-SL
     */
    private int assetType;

    /**
     * 数量
     */
    private  double amount;

    /**
     * 资产状态：0-盲盒NFT已购买；1-盲盒NFT已使用；2-具有玩星光的资格
     */
    private int assetStatus;

    /**
     * 资产属性：NFT的基本属性，数据以json字符串存放
     */
    private String assetAttrs;
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

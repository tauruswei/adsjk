package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "CosdStakeForSLReq",description = "用户向星光池质押 COSD 请求实体")
public class CosdStakeForSLReq {
    @ApiModelProperty(value = "交易id",name = "txId",example = "0xd8087868c441c49d73be3b4eb74ceffd7bf3c6ae7fecd83ed2651ba44964a8d5",dataType = "String",required = true)
    @NotBlank(message="txid cannot be empty.")
    private String txId;
    @ApiModelProperty(value = "交易类型",name = "transType",example = "2，说明：0-用户使用 USDT 购买 COSD、1-用户质押COSD 到 DEFI、2-用户质押 COSD到星光、3-质押 COSD 到俱乐部老板质押池、4-用户从 defi 提现 COSD、5-用户从 星光池中 提现 COSD、6-用户从 俱乐部老板质押池中 提现 COSD、7-用户使用USDT 购买 EVIC、8-用户提现EVIC、9-用户使用USDT 购买 NFT 盲盒、10-NFT交易",dataType = "Integer",required = true)
    private Integer transType;
    @ApiModelProperty(value = "用户id",name = "userId",example = "123",dataType = "Long",required = true)
    private Long fromUserId;
    @ApiModelProperty(value = "资产类型",name = "fromAssetType",example = "1，说明：资产类型 0-USDT；1-COSD；2-NFT；3-EVIC",dataType = "Integer",required = true)
    private Integer fromAssetType;
    @ApiModelProperty(value = "数量",name = "amount",example = "400.5",dataType = "Double",required = true)
    private Double fromAmount;
    @ApiModelProperty(value = "用户id",name = "userId",example = "123",dataType = "Long",required = false)
    private Long toUserId;
    @ApiModelProperty(value = "test",name = "fromAssetType",example = "1，说明：资产类型 0-USDT；1-COSD；2-NFT；3-EVIC",dataType = "Integer",required = false)
    private Integer toAssetType;
    @ApiModelProperty(value = "数量",name = "amount",example = "400.5",dataType = "Double",required = false)
    private Double toAmount;
    @ApiModelProperty(value = "备注",name = "remark",example = "备注",dataType = "String",required = false)
    private String remark;
}


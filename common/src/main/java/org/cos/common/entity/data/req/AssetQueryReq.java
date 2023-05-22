package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "AssetQueryReq",description = "用户资产查询请求实体")
public class AssetQueryReq {
    @ApiModelProperty(value = "用户id",name = "userId",example = "123" ,required = true)
    private Long userId;
    @ApiModelProperty(value = "资产类型",name = "assetType",example = "1" ,required = false)
//    @NotBlank(message="Code cannot be empty.")
    private Integer assetType;
}


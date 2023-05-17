package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "NFTAttribute",description = "NFT 属性")
public class NFTAttribute {
    @ApiModelProperty(value = "NFT编号",name = "collectionId",example = "N0001",dataType = "String")
    private String collectionId;
    @ApiModelProperty(value = "NFT次数",name = "number",example = "50",dataType = "Integer")
    private String number;
}


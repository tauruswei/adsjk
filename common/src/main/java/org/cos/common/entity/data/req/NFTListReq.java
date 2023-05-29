package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "NFTListReq",description = "NFT 列表请求实体")
public class NFTListReq {
    @ApiModelProperty(value = "用户id",name = "userId",example = "1",dataType = "Integer",required = true)
    private Long userId;
    @ApiModelProperty(value = "类型",name = "status",example = " 0，状态 0-已购买；1-已使用；2-已失效",dataType = "Integer",required = true)
    private Integer status;
    private PageReq pageReq;
}

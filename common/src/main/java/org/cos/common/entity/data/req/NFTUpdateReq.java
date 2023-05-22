package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "NFTUpdateReq",description = "NFT 更新请求实体")
public class NFTUpdateReq {
    @ApiModelProperty(value = "用户id",name = "userId",example = "1",dataType = "Integer",required = true)
    private Long userId;
    @ApiModelProperty(value = "类型",name = "status",example = " 0，状态 0-已购买；1-已使用；2-已失效",dataType = "Integer",required = false)
    private Integer status;
    @ApiModelProperty(value = "属性1",name = "attr1",example = "10001",dataType = "String",required = false)
    private String attr1;
    @ApiModelProperty(value = "属性2",name = "attr2",example = "49",dataType = "String",required = false)
    private String attr2;
}


package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "TranBlurListReq",description = "交易列表模糊查询 实体")
public class TranBlurListReq {
    @ApiModelProperty(value = "交易类型",name = "transType",example = "[7,8]",dataType = "Integer",required = true)
    private int[] transType;
    @ApiModelProperty(value = "用户id",name = "userId",example = "123",dataType = "Long",required = false)
    private Long userId;
    @ApiModelProperty(value = "状态",name = "status",example = "1",dataType = "Integer",required = false)
    private int status;
    @ApiModelProperty(value = "时间戳（秒）",name = "time",example = "1000000",dataType = "Long",required = false)
    private Long time;
    @ApiModelProperty(value = "页码",name = "pageNo",example = "1" ,dataType = "Integer",required = false)
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页显示多少条数据",name = "pageSize",example = "5" ,dataType = "Integer",required = false)
    private Integer pageSize = 5;
}


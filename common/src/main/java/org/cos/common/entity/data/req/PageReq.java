package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "PageReq",description = "分页查询实体")
public class PageReq {
    @ApiModelProperty(value = "页码",name = "pageNo",example = "1" ,dataType = "Integer",required = false)
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页显示多少条数据",name = "pageSize",example = "5" ,dataType = "Integer",required = false)
    private Integer pageSize = 5;
}

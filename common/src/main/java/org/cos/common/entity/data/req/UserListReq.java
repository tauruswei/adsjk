package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UserListReq",description = "用户 列表请求实体")
public class UserListReq {
    @ApiModelProperty(value = "用户类型",name = "userType",example = "1",dataType = "Integer",required = true)
    private int userType;
    @ApiModelProperty(value = "页码",name = "pageNo",example = "1" ,dataType = "Integer",required = false)
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页显示多少条数据",name = "pageSize",example = "5" ,dataType = "Integer",required = false)
    private Integer pageSize = 5;
}


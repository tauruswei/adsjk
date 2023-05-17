package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "UserQueryByInviterIdReq",description = "根据 inviterId 分页查询用户请求实体")
public class UserQueryByInviterIdReq {

    @ApiModelProperty(name = "page",value = "页数",example = "1")
    Integer page;
    @ApiModelProperty(name = "limit",value = "每页显示多少条数据",example = "10")
    Integer limit;
    @ApiModelProperty(value = "邀请用户的id",name = "inviterId",example = "123" ,required = false)
//    @NotBlank(message="Code cannot be empty.")
    private Integer inviterId;

}
//@Data
//@ApiModel("SearchAppRequestModel(查询应用请求实体)")
//public class SearchAppRequestModel {
//    @ApiModelProperty(name = "appId",value = "应用Id",example = "452818186333859840")
//    String appId;
//    @ApiModelProperty(name = "appName",value = "应用名称",example = "sansec")
//    String appName;
//    @ApiModelProperty(name = "page",value = "页数",example = "1")
//    Integer page;
//    @ApiModelProperty(name = "limit",value = "每页显示多少条数据",example = "10")
//    Integer limit;
//}


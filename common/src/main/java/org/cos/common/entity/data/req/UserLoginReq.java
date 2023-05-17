package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "UserLoginReq",description = "创建用户请求实体")
public class UserLoginReq {
    @ApiModelProperty(value = "用户名称",name = "name",example = "cosd",required = false)
    private String name;
    @ApiModelProperty(value = "用户密码",name = "passwd",example = "cosd@123.com" ,required = true)
    @NotBlank(message="Password cannot be empty.")
    private String passwd;
    @ApiModelProperty(value = "邮箱", dataType = "string",name = "emailAddress",example = "13156050650@163.com",required = false)
//    @Email(message = "Email format is incorrect,please change the email")
    private String email;
}


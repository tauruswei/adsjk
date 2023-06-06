package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "UserCreateReq",description = "创建用户请求实体")
public class UserCreateReq {
    @ApiModelProperty(value = "用户名称",name = "name",example = "cosd",required = true)
    @Pattern(regexp = "^[a-zA-Z]([_a-zA-Z0-9]{0,63})$", message = "name need to begin with letters,composed of letters，numbers and '_'，and have a length of 1 to 64.")
    private String name;
    @ApiModelProperty(value = "用户密码",name = "passwd",example = "cosd@123.com" ,required = true)
    @NotBlank(message="Password cannot be empty.")
    private String passwd;
    @ApiModelProperty(value = "邮箱", dataType = "string",name = "emailAddress",example = "13156050650@163.com",required = true)
    @Email(message = "Email format is incorrect,please change the email")
    private String email;
    @ApiModelProperty(value = "钱包地址",name = "walletAddress",example = "0xccb233A8269726c51265cff07fDC84110F5F3F4c" ,required = true)
    @NotBlank(message="Wallet address cannot be empty.")
    private String walletAddress;
    @ApiModelProperty(value = "邮箱验证码",name = "code",example = "123456" ,required = true)
    @NotBlank(message="Code cannot be empty.")
    private String code;
    @ApiModelProperty(value = "邀请用户的id",name = "inviterId",example = "123" ,required = false)
//    @NotBlank(message="Code cannot be empty.")
    private String inviterId;

    @ApiModelProperty(value = "用户类型",name = "userType",example = "123" ,required = true)
//    @NotBlank(message="Code cannot be empty.")
    private Integer userType;
}


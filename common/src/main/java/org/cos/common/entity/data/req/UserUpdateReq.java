package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "UserUpdateReq",description = "更新用户请求实体")
public class UserUpdateReq {
    @ApiModelProperty(value = "用户Id",name = "id",example = "1",required = true)
//    @Pattern(regexp = "^[a-zA-Z]([_a-zA-Z0-9]{0,63})$", message = "name need to begin with letters,composed of letters，numbers and '_'，and have a length of 1 to 64.")
    private Long userId;

    @ApiModelProperty(value = "用户名称",name = "name",example = "cosd",required = false)
//    @Pattern(regexp = "^[a-zA-Z]([_a-zA-Z0-9]{0,63})$", message = "name need to begin with letters,composed of letters，numbers and '_'，and have a length of 1 to 64.")
    private String name;
    @ApiModelProperty(value = "用户旧密码",name = "oldPasswd",example = "cosd@123.com" ,required = false)
//    @NotBlank(message="Password cannot be empty.")
    private String oldPasswd;
    @ApiModelProperty(value = "用户新密码",name = "passwd",example = "cosd@123.com" ,required = false)
//    @NotBlank(message="Password cannot be empty.")
    private String newPasswd;
    @ApiModelProperty(value = "邮箱", dataType = "string",name = "emailAddress",example = "13156050650@163.com",required = false)
    private String email;
    @ApiModelProperty(value = "钱包地址",name = "walletAddress",example = "0xccb233A8269726c51265cff07fDC84110F5F3F4c" ,required =false)
//    @NotBlank(message="Wallet address cannot be empty.")
    private String walletAddress;
    @ApiModelProperty(value = "邮箱验证码",name = "code",example = "123456" ,required = false)
//    @NotBlank(message="Code cannot be empty.")
    private String code;
    @ApiModelProperty(value = "用户状态",name = "status",example = "0" ,required = false)
//    @NotBlank(message="Code cannot be empty.")
    private int status;

}


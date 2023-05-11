package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@ApiModel(value = "UserSendCodeReq",description = "生成邮箱验证码请求实体")

public class UserSendCodeReq {
    @ApiModelProperty(value = "邮箱", dataType = "string",name = "emailAddress",example = "13156050650@163.com",required = true)
    @Email(message = "Email format is incorrect,please change the email")
    private String email;
}


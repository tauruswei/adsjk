package org.cos.common.entity.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ApiModel(value = "CreateUserReq",description = "创建用户请求实体")

public class CreateUserReq {
//    @Pattern(regexp = "^[a-zA-Z]([_a-zA-Z0-9]{0,63})$", message = "name need to begin with letters,composed of letters，numbers and '_'，and have a length of 1 to 64.")
    private String name;
//    @NotBlank(message="Password cannot be empty.")
    private String password;
    @Email(message = "Email format is incorrect,please change the email")
    @ApiModelProperty(value = "邮箱", dataType = "string",name = "emailAddress",example = "Swxa@sansec.com.cn",required = false)
    private String email;
//    @NotBlank(message="Wallet address cannot be empty.")
    private String walletAddress;
//    @NotBlank(message="Code cannot be empty.")
    private String code;
}


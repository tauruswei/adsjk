package org.cos.common.entity.sys.param;

import lombok.Data;

@Data
public class ForgetPasswordParam {
    private String password;
    private String passwordAgain;
    private String verificationCode;
    private String userAccount;
}

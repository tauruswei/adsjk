package org.cos.common.entity.sys.param;

import lombok.Data;

@Data
public class SendVerificationCodeParam {
    private String email;
    private String cellphone;
    private Integer codeType;
}

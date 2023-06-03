package org.cos.common.entity.data.vo;

import lombok.Data;

@Data
public class UserLoginVo {
    private String walletAddress;
    private String token;
    private String userName;
    private Long userId;
    private int userType;
}

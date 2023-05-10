package org.cos.common.entity.sys.vo;

import lombok.Data;

@Data
public class SysUserLoginVO {
    private Long userId;
    private String userAccount;
    private String userName;
    private boolean isForbid;
}

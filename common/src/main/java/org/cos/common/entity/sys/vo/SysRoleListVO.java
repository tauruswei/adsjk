package org.cos.common.entity.sys.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.cos.common.entity.sys.po.SysRole;

import java.util.Date;

@Data
public class SysRoleListVO extends SysRole {
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;
}

package org.cos.common.entity.sys.vo;

import lombok.Data;
import org.cos.common.entity.sys.po.SysAuth;

import java.util.List;

@Data
public class SysAuthNodeVO extends SysAuth {
    /**
     * 子节点
     */
    private List<SysAuthNodeVO> children;
    /**
     * 是否授权
     */
    private int isGrant;
}

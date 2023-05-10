package org.cos.common.entity.sys.po;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysOrganFusion {
    private String serverAddress;
    private boolean registered;
    private boolean show;
}

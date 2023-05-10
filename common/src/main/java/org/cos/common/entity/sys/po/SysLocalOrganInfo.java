package org.cos.common.entity.sys.po;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SysLocalOrganInfo {
    private String organId;
    private String organName;
    private String pinCode;
    private String gatewayAddress;

    private Map<String,SysOrganFusion> fusionMap;
    private List<SysOrganFusion> fusionList;

    private Map<String,Object> homeMap;
}

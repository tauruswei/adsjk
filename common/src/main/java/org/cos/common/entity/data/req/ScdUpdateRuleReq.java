package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class ScdUpdateRuleReq {
    private Long id;
    private String name;
    private String attrName;
    private String type;
    private int value;
}


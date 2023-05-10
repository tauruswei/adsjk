package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class ScdCreateRuleReq {
    String name;
    String attrName;
    String type;
    int value;
}


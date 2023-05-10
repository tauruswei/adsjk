package org.cos.common.entity.data.req;

import lombok.Data;

import java.util.List;

@Data
public class ScdCreateTemplateReq {
    private String name;
    private List<String> attrs;
}


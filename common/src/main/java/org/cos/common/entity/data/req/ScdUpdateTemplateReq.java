package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class ScdUpdateTemplateReq {
    private Long id;
    private String certificate;
    private String priKey;
    private String path;
}


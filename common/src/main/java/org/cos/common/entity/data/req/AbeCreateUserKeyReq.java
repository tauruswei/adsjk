package org.cos.common.entity.data.req;

import lombok.Data;

import java.util.List;

@Data
public class AbeCreateUserKeyReq {
    private Long projectId;
    private List<String> attrs;
}


package org.cos.common.entity.sys.param;

import lombok.Data;

@Data
public class ChangeLocalOrganInfoParam {
    private String organName;
    private String gatewayAddress;
    private String globalId;
}

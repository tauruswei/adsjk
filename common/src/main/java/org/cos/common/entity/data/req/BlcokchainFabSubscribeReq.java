package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class BlcokchainFabSubscribeReq {
    private String type;
    private String callbackURL;
    private String chaincode;
    private String event;
}


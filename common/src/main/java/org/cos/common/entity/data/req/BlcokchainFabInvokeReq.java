package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class BlcokchainFabInvokeReq {
    private String chaincode;
    private String[] args;
}


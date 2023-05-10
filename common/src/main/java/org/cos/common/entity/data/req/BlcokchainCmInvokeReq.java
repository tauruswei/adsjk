package org.cos.common.entity.data.req;

import lombok.Data;

@Data
public class BlcokchainCmInvokeReq {
    private String chainId;
    private String contractName;
    private String methodName;
    private String[] parameters;
    private String contractAddr;
    private String notes;
    private String extra_msg;
}


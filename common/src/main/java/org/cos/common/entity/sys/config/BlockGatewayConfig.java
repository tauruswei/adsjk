package org.cos.common.entity.sys.config;

import lombok.Data;

@Data
public class BlockGatewayConfig {
    private String gatewayAddress;
    private Integer gatewayPort;
    private  String channelName;
}
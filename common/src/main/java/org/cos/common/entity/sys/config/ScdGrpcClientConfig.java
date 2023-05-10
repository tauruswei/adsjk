package org.cos.common.entity.sys.config;

import lombok.Data;

@Data
public class ScdGrpcClientConfig {
    private String grpcClientAddress;
    private Integer grpcClientPort;
}
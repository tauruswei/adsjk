package org.cos.common.entity.sys.config;

import lombok.Data;

@Data
public class LokiConfig {
    private String address;
    private String job;
    private String container;
}
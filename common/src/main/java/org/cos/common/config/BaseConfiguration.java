package org.cos.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.myapp")
public class BaseConfiguration {
    private String mailNickName;
    private String tokenTimeOut;
    private int slAmount;
    private int clubAmount;
    private String redisChannel;
    private String cipherKey;
    private String allowedOrigins;
}

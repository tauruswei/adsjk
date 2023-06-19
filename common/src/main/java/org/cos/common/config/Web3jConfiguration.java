package org.cos.common.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "web3j")
public class Web3jConfiguration {
    private Map<String,NetworkConfig> networkConfig;

    // getters and setters
    @Data
    public static class NetworkConfig {
        private String networkName;
        private String nativeCurrency;
        @Value("#{'${web3j.rpcUrls}'.split(',')}")
        private String[] rpcUrls;
        private String explorer;
        private Integer blockNumber;
        private Integer chainId;
        @JsonIgnore
        private String privateKey;
        private Map<String, ContractProperties> contract;

        // getters and setters
        @Data
        public static class ContractProperties {
            private String address;
            private String abi;
            private String image;
        }
    }
}


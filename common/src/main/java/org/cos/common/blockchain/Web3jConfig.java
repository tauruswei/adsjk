package org.cos.common.blockchain;

import org.cos.common.config.Web3jConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

@Configuration
public class Web3jConfig {
    @Autowired
    private Web3jConfiguration web3jConfiguration;
//    @Value("${web3j.networkConfig.bsc.client-address}")
//    private String clientAddress;
//    @Value("${web3j.networkConfig.bsc.contractAddress}")
//    private String contractAddress;
    @Bean
    public Web3j web3j() {
        // todo 网络名称,网络链接
        return Web3j.build(new HttpService(web3jConfiguration.getNetworkConfig().get("bsc").getRpcUrls()[0]));
    }

//    @Bean(name = "inviteFilter") // 如果你有多个过滤器，你需要指定每个过滤器的名字
//    @Scope("prototype") // 你可能要同时监听多个事件，那么就不能使用同一个实例，因此这里需要每次都生成一个新的对象
//    public EthFilter ethFilter(Web3j web3j) throws IOException {
//        // 方式二：通过智能合约地址。这种方式不需要我们把智能合约转为java类，更加灵活。
//        return new EthFilter(
//                DefaultBlockParameterName.EARLIEST,
//                DefaultBlockParameterName.LATEST,
//                contractAddress
//        );
//    }
}

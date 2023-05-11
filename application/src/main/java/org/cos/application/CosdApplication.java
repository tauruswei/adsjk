package org.cos.application;

//import com.primihub.biz.config.mq.SingleTaskChannel;
import org.cos.common.config.BaseConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages="org.cos")
@EnableConfigurationProperties(BaseConfiguration.class)
@EnableAsync
// 如果使用 Spring Boot 2.3.x 及以上版本，需要使用 @MapperScan 注解来指定扫描路径
@MapperScan("org.cos.common.repository")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CosdApplication {
    public static void main(String[] args) {
        SpringApplication.run(CosdApplication.class, args);
    }

}

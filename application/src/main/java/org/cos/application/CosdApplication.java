package org.cos.application;

//import com.primihub.biz.config.mq.SingleTaskChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages="org.cos")
@EnableAsync
public class CosdApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosdApplication.class, args);
    }

}

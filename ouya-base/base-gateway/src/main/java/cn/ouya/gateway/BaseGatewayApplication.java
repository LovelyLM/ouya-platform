package cn.ouya.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cn.ouya.**")
public class BaseGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseGatewayApplication.class, args);
    }
}

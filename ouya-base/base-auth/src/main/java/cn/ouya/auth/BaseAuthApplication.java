package cn.ouya.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BaseAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseAuthApplication.class, args);
    }
}

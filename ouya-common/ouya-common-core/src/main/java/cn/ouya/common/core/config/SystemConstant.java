package cn.ouya.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 公共常量
 *
 * @author yuzhiheng
 * @date 2021-07-26 13:32
 */
@Data
@Component
@PropertySource(value = "classpath:bootstrap.yml", encoding = "UTF-8")
@ConfigurationProperties(prefix = "spring.application")
public class SystemConstant implements Serializable {

    /**
     * 服务名
     */
    public String name;
}

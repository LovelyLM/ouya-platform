package cn.ouya.common.core.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author ：yuzhiheng
 * @date ：2021-08-11 10:56
 */
@Configuration
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     * 允许相同名称的bean被覆盖。(远程feign调用接口)
     */
    @Override
    public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {
        application.setAllowBeanDefinitionOverriding(true);
    }
}
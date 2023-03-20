package cn.ouya.common.core.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.same.SaSameUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.ouya.common.base.enums.SystemExceptionEnum;
import cn.ouya.common.base.factory.ExceptionFactory;
import cn.ouya.common.satoken.config.StpUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static cn.ouya.common.base.enums.BizExceptionEnum.NEED_LOGIN_ERROR;

/**
 * 权限安全配置
 *
 * @author Lion Li
 */
@Configuration
@Slf4j
public class SecurityConfiguration implements WebMvcConfigurer {
    @Autowired
    private SystemConstant systemConstant;

    /**
     * 注册sa-token的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义验证规则
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }

    /**
     * 校验是否从网关转发
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/auth/**")
                .setAuth(e -> {
                    // 检查是否从网关转发
                    SaSameUtil.checkCurrentRequestToken();
                    String serverName = systemConstant.name;
                    log.error(serverName);
                    if (serverName.equals("ouya-product")) {
                        StpUtil.checkLogin();
                    } else{
                        StpUserUtil.checkLogin();
                    }
                })
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*")
                            // 提示浏览器json格式返回
                            .setHeader("Content-Type", "application/json; charset=utf-8");
                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> log.info("-------- OPTIONS预检请求，不做处理"))
                            .back();
                })
                .setError(e -> {
                    log.error(SystemExceptionEnum.NOT_BY_GATEWAY_ERROR.getMessage());
                    return SaResult.error(SystemExceptionEnum.NOT_BY_GATEWAY_ERROR.getMessage()).setCode(SystemExceptionEnum.NOT_BY_GATEWAY_ERROR.getCode());
                });
    }
}

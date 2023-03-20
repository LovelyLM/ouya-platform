package cn.ouya.gateway.filter;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import cn.ouya.common.base.enums.BizExceptionEnum;
import cn.ouya.common.base.factory.Assert;
import cn.ouya.common.base.factory.ExceptionFactory;
import cn.ouya.gateway.config.StpUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.ouya.common.base.enums.BizExceptionEnum.NEED_LOGIN_ERROR;

/**
 * [Sa-Token 权限认证] 拦截器
 *
 */
@Configuration
@Slf4j
public class AuthFilter {

    /**
     * 注册 Sa-Token 全局过滤器
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter().addInclude("/**")
                .addExclude("/ouya-auth/**")
                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由
                    SaRouter.match("/**")
//                    .notMatch(ignoreWhite.getWhites())
                            .check(r -> {
                                // 检查是否登录 是否有token
                                if (!StpUserUtil.isLogin() && !StpUtil.isLogin()) {
                                    throw ExceptionFactory.logicException(NEED_LOGIN_ERROR);
                                }
                            });
                })
                .setError(e -> {
                    log.error(String.valueOf(e));
                    return SaResult.error(BizExceptionEnum.NEED_LOGIN_ERROR.getMessage()).setCode(BizExceptionEnum.NEED_LOGIN_ERROR.getCode());
                })
                // 前置函数：在每次认证函数之前执行
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
                            .setHeader("Content-Type", "application/json");
                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> log.info("-------- OPTIONS预检请求，不做处理"))
                            .back();
                });
    }
}

package cn.ouya.gateway.filter;

import cn.dev33.satoken.same.SaSameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 转发认证过滤器(内部服务外网隔离)
 *
 * @author Lion Li
 */
@Component
@Slf4j
public class ForwardAuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 忽略打印Head请求
        if (!HttpMethod.HEAD.equals(exchange.getRequest().getMethod())) {
            log.info("HttpMethod:[{}] Uri:[{}]", exchange.getRequest().getMethod(), exchange.getRequest().getURI());
        }
        ServerHttpRequest newRequest = exchange
            .getRequest()
            .mutate()
            // 为请求追加 Same-Token 参数
            .header(SaSameUtil.SAME_TOKEN, SaSameUtil.getToken())
            .build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}


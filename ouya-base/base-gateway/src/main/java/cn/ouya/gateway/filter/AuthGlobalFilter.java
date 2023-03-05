package cn.ouya.gateway.filter;


import com.alibaba.nacos.api.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * <p>
 * 全局Filter
 * </p>
 *
 * @author yuzhiheng
 * @since 2021-06-28 16:04
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final String FILTERED_URI = "/actuator";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();



        // 忽略打印Head请求
        if (!HttpMethod.HEAD.equals(request.getMethod())) {
            log.info("HttpMethod:[{}] Uri:[{}]", request.getMethod(), request.getURI());
        }


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}

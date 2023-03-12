package cn.ouya.gateway.utils;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.ouya.common.base.enums.BizExceptionEnum;
import cn.ouya.common.base.enums.SystemExceptionEnum;
import cn.ouya.common.base.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;

import java.nio.charset.StandardCharsets;

/**
 * 网关统一异常处理
 *
 * @author ruoyi
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        CommonResponse<Void> commonResponse;

        if (ex instanceof NotFoundException) {
            commonResponse = CommonResponse.fail(SystemExceptionEnum.SERVICE_NOT_FOUND_ERROR);
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            commonResponse = CommonResponse.failMsg(responseStatusException.getMessage());
        } else {
            commonResponse = CommonResponse.fail(SystemExceptionEnum.INTERNAL_SERVER_ERROR);
        }

        log.error("HttpMethod:[{}] Uri:[{}] Exception:[{}] Message:[{}]", exchange.getRequest().getMethod(), exchange.getRequest().getURI(), ex, ex.getMessage());

        DataBuffer dataBuffer = response.bufferFactory()
                .allocateBuffer()
                .write(JSONUtil.toJsonStr(commonResponse, JSONConfig.create().setIgnoreNullValue(false)).getBytes(StandardCharsets.UTF_8));
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(dataBuffer)));
    }
}

package cn.ouya.product.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.http.HttpUtil;
import cn.ouya.common.base.response.CommonResponse;
import cn.ouya.common.redis.server.RedisService;
import cn.ouya.common.satoken.config.StpUserUtil;

import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import com.unfbx.chatgpt.entity.images.ImageResponse;
import com.unfbx.chatgpt.entity.images.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@Validated
public class Test {

    @Resource
    private RedisService redisService;

    @SaCheckLogin
    @GetMapping("leiming/get")
    @Cacheable(cacheManager = "caffeineCacheManager", cacheNames = "get")
    public CommonResponse<String> get(String key) {
        log.info("请求成功:{}", key);

        Integer cacheObject = redisService.getCacheObject(key, Integer.class);
        return  CommonResponse.success("ceshi");
    }

    @SaCheckLogin(type = StpUserUtil.TYPE)
    @SaCheckPermission(value = "{leiming}")
    @Cacheable(cacheManager = "redisCacheManager", cacheNames = "set")
    @GetMapping("leiming/set")
    public CommonResponse<String> set(String key) {
        log.info("请求成功:{}", key);
        Integer cacheObject = redisService.getCacheObject(key, Integer.class);

        return  CommonResponse.success("phone");
    }

    @Data
    @AllArgsConstructor
    public static class Student implements Serializable {
        private String name;
        private Integer no;

    }

}

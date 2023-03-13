package cn.ouya.product.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.ouya.common.base.response.CommonResponse;
import cn.ouya.common.redis.server.RedisService;
import cn.ouya.common.satoken.config.StpUserUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@Slf4j
@Validated
public class Test {

    @Resource
    private RedisService redisService;

    @SaCheckLogin
    @GetMapping("leiming/get")
    public CommonResponse<Integer> get(String key) {
        log.info("请求成功:{}", key);

        Integer cacheObject = redisService.getCacheObject(key, Integer.class);
        return  CommonResponse.success(cacheObject);
    }

    @SaCheckLogin(type = StpUserUtil.TYPE)
    @GetMapping("leiming/set")
    public CommonResponse set(String key) {
        log.info("请求成功:{}", key);
        Integer cacheObject = redisService.getCacheObject(key, Integer.class);

        return  CommonResponse.success();
    }

    @Data
    @AllArgsConstructor
    public static class Student implements Serializable {
        private String name;
        private Integer no;

    }

}

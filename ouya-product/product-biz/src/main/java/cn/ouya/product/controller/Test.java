package cn.ouya.product.controller;

import cn.ouya.common.core.response.CommonResponse;
import cn.ouya.common.redis.server.RedisService;
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

    @GetMapping("leiming/get")
    public CommonResponse<Integer> get(String key) {
        log.info("请求成功:{}", key);

        Integer cacheObject = redisService.getCacheObject(key, Integer.class);
        return  CommonResponse.success(cacheObject);
    }

    @GetMapping("leiming/set")
    public CommonResponse set(String key) {
        log.info("请求成功:{}", key);
        redisService.setCacheObject("leiming", new Student("雷鸣", 123));

        return  CommonResponse.success();
    }

    @Data
    @AllArgsConstructor
    public static class Student implements Serializable {
        private String name;
        private Integer no;

    }

}

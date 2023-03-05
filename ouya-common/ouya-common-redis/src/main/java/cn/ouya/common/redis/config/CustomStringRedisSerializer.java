package cn.ouya.common.redis.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * 重写 StringRedisSerializer ,支持Object类型
 *
 * @author ：yuzhiheng
 * @date ：2021-09-15 10:57
 */
public class CustomStringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public CustomStringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }


    public CustomStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(@Nullable Object object) {

        if (object == null) {
            return null;
        }

        if (StrUtil.isEmpty(object.toString())) {
            return null;
        }

        return object.toString().getBytes(charset);
    }

}

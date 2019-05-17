package com.lizhi.miaosha.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis配置
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;
    private int port;
    private int timeout;
    private String password;
    @Value("${spring.redis.pool.max-active}")
    private int poolMaxTotal;
    @Value("${spring.redis.pool.max-idle}")
    private int poolMaxIdle;
    @Value("${spring.redis.pool.max-wait}")
    private int poolMaxWait;
}

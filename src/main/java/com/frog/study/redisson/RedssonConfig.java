package com.frog.study.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author shaohaichao
 * @since 2021/10/12
 */
public class RedssonConfig {
    public static RedissonClient redisson() {
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s", "127.0.0.1", "6379");
        config.useSingleServer().setAddress(redisUrl).setPassword("123456").setDatabase(0);
        return Redisson.create(config);
    }
}

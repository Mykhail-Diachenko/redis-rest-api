package com.flexsolution.homeassigment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
@Profile("!test")
public class RedisConfig {

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.host}")
    private String host;


    @Bean
    JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    JedisPool jedisPool() {
        return new JedisPool(jedisPoolConfig(), host, port);
    }
}

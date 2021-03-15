package com.flexsolution.homeassigment.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PreDestroy;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class JedisConnectionFactory {

    private final JedisPool jedisPool;

    public JedisConnectionFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @PreDestroy
    private void destroyPool() {
        jedisPool.close();
    }

    public void useConnection(Consumer<Jedis> consumer) {
        try (Jedis jedis = jedisPool.getResource()) {
            consumer.accept(jedis);
        }
    }

    public <T> T useConnection(Function<Jedis, T> function) {
        try (Jedis jedis = jedisPool.getResource()) {
            return function.apply(jedis);
        }
    }
}

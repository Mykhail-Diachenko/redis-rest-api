package com.flexsolution.homeassigment.messages;

import com.flexsolution.homeassigment.config.JedisConnectionFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
public class MessagesServiceTestConfiguration {

    @Bean
    JedisPool jedisPool () {
        JedisPool jedisPoolMock = Mockito.mock(JedisPool.class);

        Jedis jedisConnectionMock = Mockito.mock(Jedis.class);
//        Mockito.when(jedisConnectionMock.zadd(Mockito.any(),Mockito.any(), Mockito.any())).

        Mockito.when(jedisPoolMock.getResource()).thenReturn(jedisConnectionMock);
        return jedisPoolMock;
    }
}

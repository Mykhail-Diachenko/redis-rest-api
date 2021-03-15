package com.flexsolution.homeassigment.messages;

import com.flexsolution.homeassigment.config.JedisConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MessagesServiceTest {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;


    @Test
    public void checkDependencyInjection () {
        Assertions.assertNotNull(messagesService);
    }

    @Test
    public void checkAddMethodUsesJedisConnectionFactory () {
        JedisConnectionFactory jcfSpy = spy(jedisConnectionFactory);
        MessagesService messageServiceSpy = spy(messagesService);
        when(messageServiceSpy.getJedisConnectionFactory()).thenReturn(jcfSpy);
        messageServiceSpy.add("new message");

        verify(jcfSpy, times(1)).useConnection((Function<Jedis, Object>) any());
    }

    @Test
    public void checkGetLastMethodUsesJedisConnectionFactory () {
        JedisConnectionFactory jcfSpy = spy(jedisConnectionFactory);
        MessagesService messageServiceSpy = spy(messagesService);
        when(messageServiceSpy.getJedisConnectionFactory()).thenReturn(jcfSpy);
        messageServiceSpy.getLast();

        verify(jcfSpy, times(1)).useConnection((Function<Jedis, Object>) any());
    }

    @Test
    public void checkGetAllBetweenMethodUsesJedisConnectionFactory () {
        JedisConnectionFactory jcfSpy = spy(jedisConnectionFactory);
        MessagesService messageServiceSpy = spy(messagesService);
        when(messageServiceSpy.getJedisConnectionFactory()).thenReturn(jcfSpy);
        messageServiceSpy.getAllBetween(0, 0);

        verify(jcfSpy, times(1)).useConnection((Function<Jedis, Object>) any());
    }

    @Test
    void testTimestampToScore () {
        double result = messagesService.timestampToScore(1000L);
        Assertions.assertEquals("1000.9", String.valueOf(result));

        double result2 = messagesService.timestampToScore(8888888888888L);
        Assertions.assertEquals("8888888888888.9", BigDecimal.valueOf(result2).toPlainString());
    }

    @Test
    void testScoreToTimestamp () {
        long result = messagesService.scoreToTimestamp(1000.9);
        Assertions.assertEquals("1000", String.valueOf(result));

        long result2 = messagesService.scoreToTimestamp(8888888888888.9);
        Assertions.assertEquals("8888888888888", String.valueOf(result2));
    }
}

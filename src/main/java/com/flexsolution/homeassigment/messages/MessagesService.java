package com.flexsolution.homeassigment.messages;


import com.flexsolution.homeassigment.config.JedisConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Tuple;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessagesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagesService.class);

    private final JedisConnectionFactory jedisConnectionFactory;

    private final String messagesSetName = "messages";

    public MessagesService (JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    /**
     * Adds new message to store. Overrides if such already exists
     *
     * @param content - message content
     */
    public Message add (String content) {
        return getJedisConnectionFactory().useConnection(jedis -> {
            final long created = System.currentTimeMillis();
            jedis.zadd(messagesSetName, timestampToScore(created), content);
            Message message = new Message(content, created);
            LOGGER.debug("Message published {}", message);
            return message;
        });
    }

    /**
     * Returns last  message from store
     */
    public Optional<Message> getLast () {
        return getJedisConnectionFactory().useConnection(jedis -> {
            Set<Tuple> tuples = jedis.zrevrangeByScoreWithScores(messagesSetName, Long.MAX_VALUE, 0, 0, 1);
            if(tuples.size() >= 1) {
                Tuple tupleWithHighestScore = tuples.iterator().next();
                Message message = new Message(tupleWithHighestScore.getElement(), scoreToTimestamp(tupleWithHighestScore.getScore()));
                LOGGER.debug("Last message found {}", message);
                return Optional.of(message);
            }
            return Optional.empty();

        });
    }

    /**
     * Returns list of messages that were added between start (inclusively) and end (inclusively) timestamps
     *
     * @param start - min date of creation (milliseconds)
     * @param end   - max date of creation (milliseconds
     */
    public List<Message> getAllBetween (long start, long end) {
        LOGGER.debug("Incoming params start={}, end={}", start, end);
        return getJedisConnectionFactory().useConnection(jedis -> {
            Set<Tuple> tuples = jedis.zrevrangeByScoreWithScores(messagesSetName, timestampToScore(end), timestampToScore(start));
            List<Message> matchedMessages = tuples.stream()
                    .map(tuple -> new Message(tuple.getElement(), scoreToTimestamp(tuple.getScore())))
                    .collect(Collectors.toList());
            Collections.reverse(matchedMessages);
            LOGGER.debug("Found following messages {}", matchedMessages);
            return matchedMessages;
        });
    }

    double timestampToScore (long timestamp) {
        LOGGER.debug("Value before conversation {}", timestamp);
        String stringRepresentation = timestamp + ".9"; // add non zero digit at the end  not to lose whole number format
        double score = Double.parseDouble(stringRepresentation);
        LOGGER.debug("Value after conversation {}", score);
        return score;
    }

    long scoreToTimestamp (double score) {
        LOGGER.debug("Value before conversation {}", score);
        String stringScore = BigDecimal.valueOf(score).toPlainString();
        String stringTimestamp = stringScore.substring(0, stringScore.lastIndexOf("."));
        long timestamp = Long.parseLong(stringTimestamp);
        LOGGER.debug("Value after conversation {}", timestamp);
        return timestamp;
    }


    JedisConnectionFactory getJedisConnectionFactory () {
        return jedisConnectionFactory;
    }
}

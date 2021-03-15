package com.flexsolution.homeassigment.messages;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Message {
    private final String content;
    private final Long created;

    public Message(String content, Long created) {
        this.content = content;
        this.created = created;
    }
}

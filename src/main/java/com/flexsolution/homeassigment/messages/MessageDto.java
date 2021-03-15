package com.flexsolution.homeassigment.messages;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDto {
    private String content;
    private Long created;

    public MessageDto () {
    }

    public MessageDto (Message message) {
        if(message != null){
            this.content = message.getContent();
            this.created = message.getCreated();
        }
    }
}

package com.flexsolution.homeassigment.messages;

import com.flexsolution.homeassigment.response.ResponseBodyDto;
import com.flexsolution.homeassigment.response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagesController.class);

    private final MessagesService messagesService;

    public MessagesController (MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/publish")
    public ResponseEntity<ResponseBodyDto> publish (@RequestBody MessageDto messageDto) {
        LOGGER.debug("Request body: {}", messageDto);
        if(messageDto.getContent() == null) {
            LOGGER.error("Message content is null");
            return ResponseBuilder.prepare(HttpStatus.BAD_REQUEST, null);
        }
        Message addedMessage = messagesService.add(messageDto.getContent());
        return ResponseBuilder.prepare(new MessageDto(addedMessage));
    }

    @GetMapping("/getLast")
    public ResponseEntity<ResponseBodyDto> getLast () {
        return messagesService.getLast()
                .map(message -> ResponseBuilder.prepare(new MessageDto(message)))
                .orElseGet(() -> ResponseBuilder.prepare(HttpStatus.NOT_FOUND, null));
    }

    @GetMapping("/getByTime")
    public ResponseEntity<ResponseBodyDto> getByTime (@RequestParam(required = false) Long start, @RequestParam(required = false) Long end) {
        LOGGER.debug("Request params start={}, end={}", start, end);
        if(start == null) {
            start = 0L;
        }
        if(end == null) {
            end = Long.MAX_VALUE;
        }
        return ResponseBuilder.prepare(messagesService.getAllBetween(start, end));
    }
}

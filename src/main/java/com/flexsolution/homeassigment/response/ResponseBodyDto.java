package com.flexsolution.homeassigment.response;

import lombok.Getter;

@Getter
public class ResponseBodyDto {

    private final int statusCode;
    private final String statusMessage;
    private final Object data;

    public ResponseBodyDto (int statusCode, String statusMessage, Object data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }
}

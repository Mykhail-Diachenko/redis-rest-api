package com.flexsolution.homeassigment.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static ResponseEntity<ResponseBodyDto> prepare (HttpStatus httpStatus, Object data) {
        ResponseBodyDto responseBodyDto = new ResponseBodyDto(httpStatus.value(), httpStatus.getReasonPhrase(), data);
        return ResponseEntity.status(httpStatus).body(responseBodyDto);
    }

    public static ResponseEntity<ResponseBodyDto> prepare (Object data) {
        return prepare(HttpStatus.OK, data);
    }
}

package com.embedika.domain.response.exception;

import com.embedika.domain.response.error.Error;
import com.embedika.domain.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(ServiceException ex) {
        log.error("ServiceException: " + ex.toString());
        return new ResponseEntity<>(ErrorResponse
                .builder()
                .error(Error
                        .builder()
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .build())
                .build(), ex.getHttpStatus());
    }
}

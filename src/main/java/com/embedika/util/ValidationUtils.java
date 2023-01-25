package com.embedika.util;

import com.embedika.domain.constant.Code;
import com.embedika.domain.response.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationUtils {
    private final Validator validator;

    public <T> void validation(T req){
        if(req != null){
            Set<ConstraintViolation<T>> result = validator.validate(req);
            if(!result.isEmpty()){
                String error = result
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((s1, s2) -> s1 + ". " + s2).orElse("");
                log.error("Запрос невалиден с ошибкой: {}", error);
                throw ServiceException
                        .builder()
                        .code(Code.BAD_INSERT_ENTRY)
                        .message(error)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build();
            }
        }
    }
}

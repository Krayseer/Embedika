package com.embedika.domain.response.error;

import com.embedika.domain.constant.Code;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private Code code;
    private String message;
}

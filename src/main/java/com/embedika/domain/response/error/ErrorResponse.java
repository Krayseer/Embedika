package com.embedika.domain.response.error;

import com.embedika.domain.response.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse implements Response {
    private Error error;
}

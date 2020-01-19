package com.trafiklab.bus.lines.controller.advice;

import com.trafiklab.bus.lines.controller.BusLinesController;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class BusLinesResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getContainingClass() == BusLinesController.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return new BusLinesSuccessResponse()
                .setStatus("Success")
                .setTimestamp(LocalDateTime.now().withNano(0))
                .setResultData((List<?>) body);
    }

    static class BusLinesSuccessResponse {

        String status;
        LocalDateTime timestamp;
        List<?> resultData;

        public String getStatus() {
            return status;
        }

        public BusLinesSuccessResponse setStatus(String status) {
            this.status = status;
            return this;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public BusLinesSuccessResponse setTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public List<?> getResultData() {
            return resultData;
        }

        public BusLinesSuccessResponse setResultData(List<?> resultData) {
            this.resultData = resultData;
            return this;
        }
    }
}

package com.example.rabbitmqtest.error;

import com.example.rabbitmqtest.common.codes.ExceptionCode;
import com.example.rabbitmqtest.common.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception Handler
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Json 정렬 Exception Handler
     *
     * @param ex JsonProcessingException
     * @return API 실패 메세지
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiResponse> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("parsing error : {}", ex.getMessage(), ex);
        return ApiResponse.fail(ex.getMessage(), ExceptionCode.PROCESSING.getCode());
    }

    /**
     * RabbitMQ 연결 Exception Handler
     *
     * @param ex AmqpConnectException
     * @return API 실패 메세지
     */
    @ExceptionHandler(AmqpConnectException.class)
    public ResponseEntity<ApiResponse> handleAmqpConnectException(AmqpConnectException ex) {
        log.error("AMQP connect error : {}", ex.getMessage(), ex);
        return ApiResponse.fail(ExceptionCode.RABBITMQ_CONNECTION_FAILED.getMessage()
                                , ExceptionCode.RABBITMQ_CONNECTION_FAILED.getCode());
    }

}

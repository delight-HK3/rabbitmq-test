package com.example.rabbitmqtest.common.codes;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Exception이 발생한 경우 출력될 HttpStatus, HttpCode, 상태 메세지
 */
@Getter
public enum ExceptionCode {

    PROCESSING(HttpStatus.BAD_REQUEST, 400, "json processing error"),
    RABBITMQ_CONNECTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 500, "RabbitMQ connection failed");

    private final HttpStatusCode status; // http 상태
    private final int code;              // http 상태 코드
    private final String message;        // 작업 후 메세지

    ExceptionCode(HttpStatus status, int code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

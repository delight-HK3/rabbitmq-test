package com.example.rabbitmqtest.error;

import com.example.rabbitmqtest.common.codes.ExceptionCode;
import com.example.rabbitmqtest.common.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception Handler
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler implements RabbitListenerErrorHandler {

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

    /**
     * RabbitMQ 커넥션에 오류가 나는 경우 동작하는 handler
     *
     * @param amqpMessage the raw message received.
     * @param channel AMQP channel for manual acks.
     * @param message the converted spring-messaging message (if available).
     * @param exception the exception the listener threw, wrapped in a
     * {@link ListenerExecutionFailedException}.
     * @return 예외를 다시 던져서 재시도
     */
    @Override
    public Object handleError(Message amqpMessage, Channel channel, org.springframework.messaging.Message<?> message
                            , ListenerExecutionFailedException exception) throws Exception {

        if(exception.getCause() instanceof AmqpIOException) {
            log.error("AMQP connect error : {}", exception.getMessage(), exception);
        }

        return exception;
    }

}

package com.example.rabbitmqtest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 큐에 등록된 메세지 리턴 component
 */
@Slf4j
@Component
public class RabbitmqMessage {

    // direct Queue 메세지
    @RabbitListener(queues = "direct.queue")
    public void directMessage(Message message){
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("direct.queue 내의 메시지 반환 : {}", body);
        // 해당 Exception은 바로 해당 Queue에 등록된 Dead Queue로 이동시키는 Exception 입니다.
        throw new AmqpRejectAndDontRequeueException("Dead Queue 테스트");
    }

    // ttl이 적용된 direct Queue 메세지
    @RabbitListener(queues = "direct.queue.ttl")
    public void directTTLMessage(Message message){
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("direct.queue.ttl 내의 메시지 반환 : {}", body);
    }

    // dead Queue 메세지
    @RabbitListener(queues = "dead.queue")
    public void deadDirectMessage(Message message){
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("dead.queue 내의 메시지 반환 : {}", body);
    }

}

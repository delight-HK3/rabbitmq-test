package com.example.rabbitmqtest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 큐에 등록된 메세지 리턴 component
 */
@Slf4j
@Component
public class RabbitmqMessage {

    // direct Queue 메세지
    @RabbitListener(queues = "direct.queue")
    public void directMessage(String message){
        log.info("direct.queue 내의 메시지 반환 : {}", message);
    }

    // ttl이 적용된 direct Queue 메세지
    @RabbitListener(queues = "direct.queue.ttl")
    public void directTTLMessage(String message){
        log.info("direct.queue.ttl 내의 메시지 반환 : {}", message);
    }

    // dead Queue 메세지
    @RabbitListener(queues = "dead.queue")
    public void deadDirectMessage(String message){
        log.info("dead.queue 내의 메시지 반환 : {}", message);
    }

}

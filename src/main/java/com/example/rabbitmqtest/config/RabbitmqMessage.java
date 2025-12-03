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

    @RabbitListener(queues = "directQueue")
    public void directMessage(String message){
        log.info("directQueue 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "fanoutQueueOne")
    public void fanoutOneMessage(String message){
        log.info("fanoutQueueOne 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "fanoutQueueTwo")
    public void fanoutTwoMessage(String message){
        log.info("fanoutQueueTwo 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "headersQueue")
    public void headersMessage(String message){
        log.info("headersQueue 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "topicQueue")
    public void topicMessage(String message){
        log.info("topicQueue 내의 메시지 반환 : {}", message);
    }

}

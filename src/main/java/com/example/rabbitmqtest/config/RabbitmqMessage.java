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

    //@RabbitListener(queues = "direct.queue")
    //public void directMessage(String message){
    //    log.info("direct.queue 내의 메시지 반환 : {}", message);
    //}

    @RabbitListener(queues = "fanout.queue.one")
    public void fanoutOneMessage(String message){
        log.info("fanout.queue.one 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "fanout.queue.two")
    public void fanoutTwoMessage(String message){
        log.info("fanout.queue.two 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "header.queue")
    public void headersMessage(String message){
        log.info("header.queue 내의 메시지 반환 : {}", message);
    }

    @RabbitListener(queues = "topic.queue")
    public void topicMessage(String message){
        log.info("topic.queue 내의 메시지 반환 : {}", message);
    }

}

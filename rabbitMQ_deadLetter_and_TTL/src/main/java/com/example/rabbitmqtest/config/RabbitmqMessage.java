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

    @RabbitListener(queues = "direct.queue")
    public void directMessage(String message){
        log.info("direct.queue 내의 메시지 반환 : {}", message);
    }


}

package com.example.rabbitmqtest.service;

import com.example.rabbitmqtest.config.RabbitmqExchangeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Queue에 존재하는 메세지 조회
 */
@Slf4j
@Service
public class MessageReceiveService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;

    public MessageReceiveService(RabbitTemplate rabbitTemplate, RabbitmqExchangeInfo rabbitmqExchangeInfo) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqExchangeInfo = rabbitmqExchangeInfo;
    }

    /**
     * Direct Queue에 등록되어 있는 메세지 Queue 불러오기
     */
    public void receiveDirectMessage() {

        Object message = rabbitTemplate.receiveAndConvert(rabbitmqExchangeInfo.get_DIRECT_QUEUE_NAME());
        if (message != null) {
            System.out.println("Received: " + message);
        }

    }

}

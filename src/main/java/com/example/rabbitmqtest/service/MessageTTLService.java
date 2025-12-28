package com.example.rabbitmqtest.service;

import com.example.rabbitmqtest.config.RabbitmqExchangeInfo;
import com.example.rabbitmqtest.dto.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TTL이 적용된 Service
 */
@Slf4j
@Service
public class MessageTTLService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;

    public MessageTTLService(RabbitTemplate rabbitTemplate, RabbitmqExchangeInfo rabbitmqExchangeInfo) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqExchangeInfo = rabbitmqExchangeInfo;
    }

    /**
     * Direct Exchange 방식 메세지에 추가로 TTL시간을 설정하여 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 성공 시 "success_direct" 리턴
     */
    public String sendDirectTTLMessage(MessageDTO messageDTO) {
        rabbitTemplate.convertAndSend(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_NAME()
                                        , rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_KEY()
                                        , messageDTO
                                        , message -> {
                                            message.getMessageProperties().setExpiration("5000");
                                            return message;
                                        });

        return "success_direct";

    }

}

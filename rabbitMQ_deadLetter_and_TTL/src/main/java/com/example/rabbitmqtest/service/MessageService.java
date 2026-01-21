package com.example.rabbitmqtest.service;

import com.example.rabbitmqtest.config.RabbitmqExchangeInfo;
import com.example.rabbitmqtest.dto.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * MessageService 서비스 Layer
 */
@Slf4j
@Service
public class MessageService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;

    public MessageService(RabbitTemplate rabbitTemplate, RabbitmqExchangeInfo rabbitmqExchangeInfo) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqExchangeInfo = rabbitmqExchangeInfo;
    }

    public String sendDirectMessage(MessageDTO messageDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            // DTO -> String 직렬화 수행
            String objectToJSON = objectMapper.writeValueAsString(messageDTO);



        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

        return "success_direct";
    }

}

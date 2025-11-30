package com.example.rabbitmqtest.service;

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
public class MesssageService {

    private final RabbitTemplate rabbitTemplate;

    public MesssageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final String BINDING_KEY = "test.key";
    private static final String DIRECT_EXCHANGE_NAME = "rabbit.direct"; // Direct Exchange 이름

    /**
     * 메세지 전송
     *
     * @param messageDTO 메세지 DTO
     */
    public void sendMessage(MessageDTO messageDTO) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJson = objectMapper.writeValueAsString(messageDTO);
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_NAME, BINDING_KEY, objectToJson);
        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

    }

}

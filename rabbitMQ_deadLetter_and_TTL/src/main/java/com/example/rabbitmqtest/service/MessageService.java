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

    /**
     * Direct Exchange 방식 메세지에 추가로 TTL시간을 설정하여 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 성공 시 "success_direct" 리턴
     */
    public String sendDirectMessage(MessageDTO messageDTO){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            // DTO -> String 직렬화 수행
            String objectToJSON = objectMapper.writeValueAsString(messageDTO);

            Message message = new Message(objectToJSON.getBytes());

            rabbitTemplate.convertAndSend(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_NAME()
                                        , rabbitmqExchangeInfo.get_DIRECT_ROUTING_KEY()
                                        , message);

        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

        return "success_direct";
    }

}

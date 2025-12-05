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
public class MesssageService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;

    public MesssageService(RabbitTemplate rabbitTemplate, RabbitmqExchangeInfo rabbitmqExchangeInfo) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqExchangeInfo = rabbitmqExchangeInfo;
    }

    /**
     * Direct Exchange 방식 메세지 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 성공 시 "success_direct" 리턴
     */
    public String sendDirectMessage(MessageDTO messageDTO) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJson = objectMapper.writeValueAsString(messageDTO);

            rabbitTemplate.convertAndSend(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_NAME()
                                        , rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_KEY()
                                        , objectToJson);
        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

        return "success_direct";
    }

    /**
     * Fanout Exchange 방식 메세지 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 성공 시 "success_fanout" 리턴
     */
    public String sendFanoutMessage(MessageDTO messageDTO){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJson = objectMapper.writeValueAsString(messageDTO);

            rabbitTemplate.convertAndSend(rabbitmqExchangeInfo.get_FANOUT_EXCHANGE_NAME()
                                        , ""
                                        , objectToJson);
        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

        return "success_fanout";
    }

    /**
     * Topic Exchange 방식 메세지 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 성공 시 "success_topic" 리턴
     */
    public String sendTopicMessage(MessageDTO messageDTO){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJson = objectMapper.writeValueAsString(messageDTO);

            rabbitTemplate.convertAndSend(rabbitmqExchangeInfo.get_TOPIC_EXCHANGE_NAME()
                                            ,"topic.send.test"
                                            ,objectToJson);
        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

        return "success_topic";
    }

    /**
     * Header Exchange 방식 메세지 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 성공 시 "success_header" 리턴
     */
    public String sendHeaderMessage(MessageDTO messageDTO){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJson = objectMapper.writeValueAsString(messageDTO);

            rabbitTemplate.convertAndSend(rabbitmqExchangeInfo.get_HEADER_EXCHANGE_NAME()
                    ,""
                    ,objectToJson);
        } catch (JsonProcessingException ex) {
            log.error("parsing error : {}", ex.getMessage(), ex);
        }

        return "success_header";
    }

}

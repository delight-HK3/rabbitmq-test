package com.example.rabbitmqtest.controller;

import com.example.rabbitmqtest.common.response.ApiResponse;
import com.example.rabbitmqtest.dto.MessageDTO;
import com.example.rabbitmqtest.service.MesssageService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반적인 Message 전송
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/publisher")
public class MessageController {

    private final MesssageService messsageService;

    public MessageController(MesssageService messsageService) {
        this.messsageService = messsageService;
    }

    /**
     * Direct Exchange 방식으로 메세지 큐 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/direct")
    public ResponseEntity<ApiResponse> sendDirectMessage(@RequestBody MessageDTO messageDTO) {
        String resultMessage = messsageService.sendDirectMessage(messageDTO);

        return ApiResponse.success(resultMessage,200);
    }

    /**
     * Fanout Exchange 방식으로 메세지 큐 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/fanout")
    public ResponseEntity<ApiResponse> sendFanoutMessage(@RequestBody MessageDTO messageDTO){
        String resultMessage = messsageService.sendFanoutMessage(messageDTO);

        return ApiResponse.success(resultMessage, 200);
    }

    /**
     * Header Exchange 방식으로 메세지 큐 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/header")
    public ResponseEntity<ApiResponse> sendHeaderMessage(@RequestBody MessageDTO messageDTO){
        String resultMessage = messsageService.sendHeaderMessage(messageDTO);

        return ApiResponse.success(resultMessage, 200);
    }

    /**
     * Topic Exchange 방식으로 메세지 큐 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/topic")
    public ResponseEntity<ApiResponse> sendTopicMessage(@RequestBody MessageDTO messageDTO){
        String resultMessage = messsageService.sendTopicMessage(messageDTO);

        return ApiResponse.success(resultMessage, 200);
    }

}

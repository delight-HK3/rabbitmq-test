package com.example.rabbitmqtest.controller;

import com.example.rabbitmqtest.common.response.ApiResponse;
import com.example.rabbitmqtest.dto.MessageDTO;
import com.example.rabbitmqtest.service.MessageService;
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

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Direct Exchange 방식 메세지 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/direct")
    public ResponseEntity<ApiResponse> sendDirectMessage(@RequestBody MessageDTO messageDTO) {
        String resultMessage = messageService.sendDirectMessage(messageDTO);

        return ApiResponse.success(resultMessage,200);
    }

}

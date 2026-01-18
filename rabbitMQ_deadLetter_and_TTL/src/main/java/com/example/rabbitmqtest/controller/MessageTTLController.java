package com.example.rabbitmqtest.controller;

import com.example.rabbitmqtest.common.response.ApiResponse;
import com.example.rabbitmqtest.dto.MessageDTO;
import com.example.rabbitmqtest.service.MessageTTLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TTL 설정된 Message 전송
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/publisher/ttl")
public class MessageTTLController {

    private final MessageTTLService messageTTLService;

    public MessageTTLController(MessageTTLService messageTTLService) {
        this.messageTTLService = messageTTLService;
    }

    /**
     * Direct Exchange 방식 메세지에 추가로 TTL시간을 설정하여 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/direct")
    public ResponseEntity<ApiResponse> sendDirectMessage(@RequestBody MessageDTO messageDTO) {
        String resultMessage = messageTTLService.sendDirectTTLMessage(messageDTO);

        return ApiResponse.success(resultMessage,200);
    }

}

package com.example.rabbitmqtest.controller;

import com.example.rabbitmqtest.service.MessageReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RabbitMQ에 저장되어있는 메세지 조회
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/publisher/receive")
public class MessageReceiveController {

    private final MessageReceiveService messageReceiveService;

    public MessageReceiveController(MessageReceiveService messageReceiveService) {
        this.messageReceiveService = messageReceiveService;
    }

    /**
     * Direct Queue에 등록되어 있는 메세지 Queue 불러오기
     */
    @GetMapping("/direct")
    public void receiveMessage() {
        messageReceiveService.receiveDirectMessage();
    }

}

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




}

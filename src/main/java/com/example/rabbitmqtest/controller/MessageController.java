package com.example.rabbitmqtest.controller;

import com.example.rabbitmqtest.common.codes.SuccessCode;
import com.example.rabbitmqtest.common.response.ApiResponse;
import com.example.rabbitmqtest.dto.MessageDTO;
import com.example.rabbitmqtest.service.MesssageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/publisher")
public class MessageController {

    private final MesssageService messsageService;

    public MessageController(MesssageService messsageService) {
        this.messsageService = messsageService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO messageDTO) {

        messsageService.sendMessage(messageDTO);
        ApiResponse ar  = ApiResponse.builder()
                    .resultMsg(SuccessCode.SELECT.getMessage())
                    .resultCode(SuccessCode.SELECT.getStatus())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }
}

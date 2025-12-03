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

    /**
     * Direct Exchange 방식으로 메세지 큐 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/direct")
    public ResponseEntity<?> sendDirectMessage(@RequestBody MessageDTO messageDTO) {
        String resultMessage = messsageService.sendDirectMessage(messageDTO);
        ApiResponse ar  = ApiResponse.builder()
                    .resultMsg(resultMessage)
                    .resultCode(SuccessCode.SELECT.getStatus())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

    /**
     * Fanout Exchange 방식으로 메세지 큐 전송
     *
     * @param messageDTO 메세지 DTO
     * @return 메세지 성공 응답
     */
    @PostMapping("/fanout")
    public ResponseEntity<?> sendFanoutMessage(@RequestBody MessageDTO messageDTO){
        String resultMessage = messsageService.sendFanoutMessage(messageDTO);
        ApiResponse ar  = ApiResponse.builder()
                .resultMsg(resultMessage)
                .resultCode(SuccessCode.SELECT.getStatus())
                .build();

        return new ResponseEntity<>(ar, HttpStatus.OK);
    }



}

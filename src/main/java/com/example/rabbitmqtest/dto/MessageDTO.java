package com.example.rabbitmqtest.dto;

import lombok.*;

/**
 * 메세지 전송하는 DTO
 */
@Getter
public class MessageDTO {
    private String title;
    private String content;
}

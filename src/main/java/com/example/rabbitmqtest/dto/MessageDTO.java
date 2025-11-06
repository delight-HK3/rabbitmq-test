package com.example.rabbitmqtest.dto;

import lombok.*;

/**
 * 메세지 전송하는 DTO
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDTO {

    private String title;
    private String content;

    @Builder
    public MessageDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

}

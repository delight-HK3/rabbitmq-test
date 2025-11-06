package com.example.rabbitmqtest.common.codes;

import lombok.Getter;

@Getter
public enum SuccessCode {

    SELECT(200,"SELECT SUCCESS");

    private final Integer status;
    private final String message;

    SuccessCode(Integer status, String message){
        this.status = status;
        this.message = message;
    }
}

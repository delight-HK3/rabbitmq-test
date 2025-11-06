package com.example.rabbitmqtest.common.response;

import lombok.Builder;
import lombok.Getter;

/**
 * API 결과를 저장하는 Response 객체
 */
@Getter
public class ApiResponse {

    private final String resultMsg;
    private final Integer resultCode;

    @Builder
    public ApiResponse(String resultMsg, Integer resultCode) {
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
    }

}

package com.example.rabbitmqtest.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * API 결과를 저장하는 Response 객체
 */
@Getter
public class ApiResponse {

    private final String resultMsg;
    private final Integer resultCode;

    public ApiResponse(String resultMsg, Integer resultCode) {
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
    }

    // API 요청 성공
    public static ResponseEntity<ApiResponse> success(String resultMsg, Integer resultCode) {
        return ResponseEntity.status(HttpStatus.OK)
                                .body(new ApiResponse(resultMsg, resultCode));
    }

    // API 요청 실패
    public static ResponseEntity<ApiResponse> fail(String resultMsg, Integer resultCode) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ApiResponse(resultMsg, resultCode));
    }

}

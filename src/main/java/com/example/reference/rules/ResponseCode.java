package com.example.reference.rules;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // 성공 코드
    SUCCESS("SUCCESS", 200, "성공");

    private final String code;
    private final int status;
    private final String message;

    ResponseCode(String code, int status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}

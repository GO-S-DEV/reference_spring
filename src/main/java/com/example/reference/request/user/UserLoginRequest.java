package com.example.reference.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Schema(description = "아이디", example = "admin")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Schema(description = "비밀번호", example = "admin")
    private String password;

}

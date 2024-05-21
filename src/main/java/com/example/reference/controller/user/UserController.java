package com.example.reference.controller.user;

import com.example.reference.request.user.AddUserRequest;
import com.example.reference.request.user.UserLoginRequest;
import com.example.reference.response.CommonResponse;
import com.example.reference.rules.ResponseCode;
import com.example.reference.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    // 유저 추가
    @PostMapping(path = "/add")
    @Operation(summary = "유저 추가", description = "유저를 추가합니다.")
    public CommonResponse<Object> add(@RequestBody AddUserRequest request) {
        userService.addUser(request);
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }


    // 유저 로그인
    @PostMapping(path = "/login")
    @Operation(summary = "로그인", description = "로그인합니다.")
    public CommonResponse<Object> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .data(token)
            .build();
    }

}

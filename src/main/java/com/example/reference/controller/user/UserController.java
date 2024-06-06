package com.example.reference.controller.user;

import com.example.reference.dto.UserDto;
import com.example.reference.request.user.AddUserRequest;
import com.example.reference.request.user.UserLoginRequest;
import com.example.reference.response.CommonResponse;
import com.example.reference.rules.ResponseCode;
import com.example.reference.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/user")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    // 유저 추가
    @PostMapping(path = "/add")
    @Operation(
        summary = "유저 추가",
        description = "유저를 추가합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AddUserRequest.class)))
    )
    public CommonResponse<Object> add(@ModelAttribute AddUserRequest request) {
        userService.addUser(request);
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }


    // 유저 로그인
    @PostMapping(path = "/login")
    @Operation(
        summary = "로그인",
        description = "로그인합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserLoginRequest.class)))
    )
    public CommonResponse<Object> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .data(token)
            .build();
    }

    // 유저 정보 조회 (getUser)
    @GetMapping(path = "/get")
    @Operation(
        summary = "유저 정보 조회",
        description = "유저 정보를 조회합니다."
    )
    public CommonResponse<UserDto> getUser() {
        UserDto user = userService.getUser();
        return CommonResponse.<UserDto>builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .data(user)
            .build();
    }

    // 유저 정보 수정 (updateUser)
    @PatchMapping(path = "/update")
    @Operation(
        summary = "유저 정보 수정",
        description = "유저 정보를 수정합니다."
    )
    public CommonResponse<Object> updateUser() {
        userService.updateUser();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 삭제 (deleteUser)
    @DeleteMapping(path = "/delete")
    @Operation(
        summary = "유저 삭제",
        description = "유저를 삭제합니다."
    )
    public CommonResponse<Object> deleteUser() {
        userService.deleteUser();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 로그아웃 (logout)
    @PostMapping(path = "/logout")
    @Operation(
        summary = "로그아웃",
        description = "로그아웃합니다."
    )
    public CommonResponse<Object> logout() {
        userService.logout();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 비밀번호 변경 (changePassword)
    @PostMapping(path = "/change-password")
    @Operation(
        summary = "비밀번호 변경",
        description = "비밀번호를 변경합니다."
    )
    public CommonResponse<Object> changePassword() {
        userService.changePassword();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 비밀번호 찾기 (findPassword)
    @PostMapping(path = "/find-password")
    @Operation(
        summary = "비밀번호 찾기",
        description = "비밀번호를 찾습니다."
    )
    public CommonResponse<Object> findPassword() {
        userService.findPassword();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 비밀번호 초기화 (resetPassword)
    @PostMapping(path = "/reset-password")
    @Operation(
        summary = "비밀번호 초기화",
        description = "비밀번호를 초기화합니다."
    )
    public CommonResponse<Object> resetPassword() {
        userService.resetPassword();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 프로필 이미지 조회 (getProfileImage)
    @GetMapping(path = "/profile-image")
    @Operation(
        summary = "프로필 이미지 조회",
        description = "프로필 이미지를 조회합니다."
    )
    public CommonResponse<UserDto> getProfileImage() {
        UserDto userDto = userService.getProfileImage();
        return CommonResponse.<UserDto>builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .data(userDto)
            .build();
    }

    // 유저 프로필 이미지 수정 (updateProfileImage)
    @PatchMapping(path = "/profile-image")
    @Operation(
        summary = "프로필 이미지 수정",
        description = "프로필 이미지를 수정합니다."
    )
    public CommonResponse<Object> updateProfileImage() {
        userService.updateProfileImage();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 프로필 이미지 삭제 (deleteProfileImage)
    @DeleteMapping(path = "/profile-image")
    @Operation(
        summary = "프로필 이미지 삭제",
        description = "프로필 이미지를 삭제합니다."
    )
    public CommonResponse<Object> deleteProfileImage() {
        userService.deleteProfileImage();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }

    // 유저 프로필 이미지 추가 (addProfileImage)
    @PostMapping(path = "/profile-image")
    @Operation(
        summary = "프로필 이미지 추가",
        description = "프로필 이미지를 추가합니다."
    )
    public CommonResponse<Object> addProfileImage() {
        userService.addProfileImage();
        return CommonResponse.builder()
            .status(ResponseCode.SUCCESS.getStatus())
            .code(ResponseCode.SUCCESS.getCode())
            .build();
    }


}

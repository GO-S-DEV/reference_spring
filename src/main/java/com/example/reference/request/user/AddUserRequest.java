package com.example.reference.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class AddUserRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Schema(description = "아이디", example = "charlie")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "비밀번호", example = "password")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(description = "닉네임", example = "찰리박")
    private String nickname;

    @Schema(description = "메모", example = "메모")
    private String memo;

    @Schema(description = "프로필 이미지", example = "이미지 파일")
    private MultipartFile profileImage;

}

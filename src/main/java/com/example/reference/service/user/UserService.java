package com.example.reference.service.user;

import com.example.reference.entity.User;
import com.example.reference.repository.UserRepository;
import com.example.reference.request.user.AddUserRequest;
import com.example.reference.request.user.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 로그인
    public String login(UserLoginRequest request) {

        User user = userRepository.findOptionalByUserId(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }



        return "token";
    }

    // 유저 로그아웃
    public void logout() {
        return;
    }

    // 유저 추가
    public void addUser(AddUserRequest request) {

        //:TODO 유효성 검사 추후에 추가

        // 계정이 이미 존재하는지 확인
        userRepository.findOptionalByUserId(request.getUserId())
            .ifPresent(user -> {
                throw new IllegalArgumentException("이미 존재하는 계정입니다.");
            });

        // 닉네임이 이미 존재하는지 확인
        userRepository.findOptionalByNickname(request.getNickname())
            .ifPresent(user -> {
                throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
            });

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 추가
        User user = User.builder()
            .userId(request.getUserId())
            .password(encodedPassword)
            .nickname(request.getNickname())
            .memo(request.getMemo())
            .build();

        userRepository.save(user);

        //:TODO 파일 저장 추후에 추가

    }

    // 유저 정보 조회
    public void getUser() {
        return;
    }

    // 유저 정보 수정
    public void updateUser() {
        return;
    }

    // 유저 삭제
    public void deleteUser() {
        return;
    }

    // 유저 목록 조회
    public void getUsers() {
        return;
    }

    // 유저 프로필 이미지 조회
    public void getProfileImage() {
        return;
    }

    // 유저 프로필 이미지 수정
    public void updateProfileImage() {
        return;
    }

    // 유저 프로필 이미지 삭제
    public void deleteProfileImage() {
        return;
    }

    // 유저 프로필 이미지 추가
    public void addProfileImage() {
        return;
    }


}

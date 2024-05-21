package com.example.reference.service.user;

import com.example.reference.entity.User;
import com.example.reference.repository.UserRepository;
import com.example.reference.request.user.AddUserRequest;
import com.example.reference.request.user.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 로그인
    public String login(UserLoginRequest request) {
        return "token";
    }

    // 유저 로그아웃
    public void logout() {
        return;
    }

    // 유저 추가
    public void addUser(AddUserRequest request) {

        // 계정이 이미 존재하는지 확인
        userRepository.findByUserId(request.getUserId())
            .ifPresent(user -> {
                throw new IllegalArgumentException("이미 존재하는 계정입니다.");
            });

        // 유저 추가
        User user = User.builder()
            .userId(request.getUserId())
            .password(request.getPassword())
            .nickname(request.getNickname())
            .memo(request.getMemo())
            .build();

        userRepository.save(user);

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

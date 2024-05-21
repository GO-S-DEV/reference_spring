package com.example.reference.service.user;

import com.example.reference.request.user.UserLoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    // 유저 로그인
    public String login(UserLoginRequest request) {
        return "token";
    }

    // 유저 로그아웃
    public void logout() {
        return;
    }

    // 유저 추가
    public void addUser() {
        return;
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

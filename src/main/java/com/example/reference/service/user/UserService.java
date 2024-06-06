package com.example.reference.service.user;

import com.example.reference.dto.UserDto;
import com.example.reference.entity.Role;
import com.example.reference.entity.User;
import com.example.reference.entity.UserRole;
import com.example.reference.repository.RoleRepository;
import com.example.reference.repository.UserRepository;
import com.example.reference.repository.UserRoleRepository;
import com.example.reference.request.user.AddUserRequest;
import com.example.reference.request.user.UserLoginRequest;
import com.example.reference.rules.ClassificationType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 로그인
    @Transactional
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
    @Transactional
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

        // Role 가져 오기
        Role role = roleRepository.findByClassification(ClassificationType.ROLE_USER.getName());

        // 권한 추가
        UserRole userRole = UserRole.builder()
            .role(role)
            .user(user)
            .build();

        userRepository.save(user);
        userRoleRepository.save(userRole);

        //:TODO 파일 저장 추후에 추가

    }

    // 유저 정보 조회
    @Transactional(readOnly = true)
    public UserDto getUser() {

        return null;
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
    public List<UserDto> getUsers() {
        return null;
    }

    // 유저 비밀번호 변경
    public void changePassword() {
        return;
    }

    // 유저 비밀번호 찾기
    public void findPassword() {
        return;
    }

    // 유저 비밀번호 초기화
    public void resetPassword() {
        return;
    }

    // 유저 프로필 이미지 조회
    public UserDto getProfileImage() {
        return null;
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

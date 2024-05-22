package com.example.reference.entity;

import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

@EqualsAndHashCode
@Comment("사용자와 권한의 관계를 묶어주는 테이블의 복합 키")
public class UserRoleId {
    @Comment("사용자")
    private User user;
    @Comment("권한")
    private Role role;
}

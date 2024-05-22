package com.example.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("사용자와 권한의 관계를 묶어주는 테이블")
public class UserRole extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED")
    @Comment("사용자")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", columnDefinition = "BIGINT UNSIGNED")
    @Comment("권한")
    private Role role;

}

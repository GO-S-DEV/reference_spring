package com.example.reference.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Comment("유저")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @Comment("인덱스")
    private long id;

    @Column(name="user_id", unique = true, nullable = false, columnDefinition = "VARCHAR(255)")
    @Comment("유저 아이디")
    private String userId;

    @Column(name="password", columnDefinition = "VARCHAR(255)")
    @Comment("유저 비밀번호")
    private String password;

    @Column(name="nickname", columnDefinition = "VARCHAR(255)", unique = true)
    @Comment("유저 닉네임")
    private String nickname;

    @Column(name="memo", columnDefinition = "TEXT")
    @Comment("유저 메모")
    private String memo;

}

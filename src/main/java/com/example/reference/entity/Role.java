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
@Table(name = "role")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @Comment("인덱스")
    private long id;

    // 권한명
    @Column(name = "title", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    @Comment("권한명")
    private String title;

    // 분류
    @Column(name = "classification", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    @Comment("분류")
    private String classification;

    // 설명
    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    @Comment("설명")
    private String description;




}

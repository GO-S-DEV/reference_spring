package com.example.reference.rules;

import lombok.Getter;

@Getter
public enum ClassificationType {

// ROLE_ADMIN, ROLE_USER

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String name;

    ClassificationType(String name) {
        this.name = name;
    }

}

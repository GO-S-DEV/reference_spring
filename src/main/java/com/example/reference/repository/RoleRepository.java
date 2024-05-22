package com.example.reference.repository;

import com.example.reference.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByClassification(String classification);

}

package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
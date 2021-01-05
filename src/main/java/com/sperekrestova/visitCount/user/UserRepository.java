package com.sperekrestova.visitCount.user;

import com.sperekrestova.visitCount.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Svetlana
 * Date: 04.01.2021
 */
interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}

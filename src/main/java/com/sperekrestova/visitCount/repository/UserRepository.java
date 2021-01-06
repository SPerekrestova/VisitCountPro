package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Svetlana
 * Date: 05.01.2021
 */

public interface UserRepository extends JpaRepository<User, Long> {

}
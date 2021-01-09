package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
public interface StudentRepository extends JpaRepository <Student, Long> {
}

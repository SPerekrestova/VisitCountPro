package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Svetlana
 * Date: 03.02.2021
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT g FROM Subject g WHERE g.name = ?1")
    public Subject findByName(String subjectName);
}

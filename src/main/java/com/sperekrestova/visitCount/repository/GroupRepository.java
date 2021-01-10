package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.model.StudyingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
public interface GroupRepository extends JpaRepository<StudyingGroup, Long> {
    @Query("SELECT g FROM StudyingGroup g WHERE g.groupName = ?1")
    public StudyingGroup findByGroupName(String groupName);
}

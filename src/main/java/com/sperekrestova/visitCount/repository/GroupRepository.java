package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.model.StudyingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
public interface GroupRepository extends JpaRepository<StudyingGroup, Long> {
}

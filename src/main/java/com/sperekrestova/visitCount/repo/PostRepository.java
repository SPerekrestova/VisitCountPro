package com.sperekrestova.visitCount.repo;

import com.sperekrestova.visitCount.model.StudyingGroup;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Svetlana
 * Date: 23.12.2020
 */
public interface PostRepository extends CrudRepository<StudyingGroup, Long> {
}

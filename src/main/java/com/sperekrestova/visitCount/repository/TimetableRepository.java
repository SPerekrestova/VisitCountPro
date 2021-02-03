package com.sperekrestova.visitCount.repository;

import com.sperekrestova.visitCount.model.Timetable;
import com.sperekrestova.visitCount.model.TimetableId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Svetlana
 * Date: 03.02.2021
 */
public interface TimetableRepository extends JpaRepository<Timetable, TimetableId> {
}

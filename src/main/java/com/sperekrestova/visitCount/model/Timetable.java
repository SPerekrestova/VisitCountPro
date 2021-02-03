package com.sperekrestova.visitCount.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Svetlana
 * Date: 17.01.2021
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TimetableId.class)
public class Timetable implements Serializable {
    @Id
    private Long subjectTableId;
    @Id
    private LocalDate date;
    @Id
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Subject subject;

    private String classroom;
    // Practice or lecture or laboratory lesson
    private String lessonType;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private StudyingGroup studyingGroup;
}

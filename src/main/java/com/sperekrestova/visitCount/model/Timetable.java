package com.sperekrestova.visitCount.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Timetable implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
    @Id
    private LocalDate date;
    @Id
    private LocalTime time;

    private String classroom;
    // Practice or lecture or laboratory lesson
    private String lessonType;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private StudyingGroup studyingGroup;
}

package com.sperekrestova.visitCount.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

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
public class Timetable {

    @EmbeddedId
    private TimetableKey key;

    private String classroom;
    // Practice or lecture or laboratory lesson
    private String lessonType;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyingGroup studyingGroup;

    @EqualsAndHashCode
    @ToString
    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimetableKey implements Serializable {

        static final long serialVersionUID = 1L;
        @ManyToOne
        private Subject subject;
        private Date date;
        private Time time;

    }
}

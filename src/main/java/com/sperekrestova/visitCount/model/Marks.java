package com.sperekrestova.visitCount.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

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
public class Marks implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    private Student student;
    @Id
    @OneToOne
    private Subject subject;
    @Id
    @OneToOne
    private Timetable timetable;

    private Mark mark;
}

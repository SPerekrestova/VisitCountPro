package com.sperekrestova.visitCount.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Svetlana
 * Date: 08.01.2021
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private Long id;

    private String firstName;
    private String secondName;
    private String lastName;

    @ManyToOne
    private StudyingGroup group;

    public Student(Long id, String firstName, String lastName, StudyingGroup group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }
}
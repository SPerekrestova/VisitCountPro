package com.sperekrestova.visitCount.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Svetlana
 * Date: 02.01.2021
 */
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToOne
    private StudyingGroup group;

    public Student() {
    }

    public Student(String firstName, String lastName, StudyingGroup group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public StudyingGroup getStudyingGroup() {
        return group;
    }

    public void setStudyingGroup(StudyingGroup groupId) {
        this.group = groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}

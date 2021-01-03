package com.sperekrestova.visitCount.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by Svetlana
 * Date: 03.01.2021
 */
@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany(mappedBy="profs")
    private List<StudyingGroup> lectureGroups;

    public Professor() {
    }

    public Professor(String firstName, String lastName, String email, String password, List<StudyingGroup> lectureGroups) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.lectureGroups = lectureGroups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<StudyingGroup> getLectureGroups() {
        return lectureGroups;
    }

    public void setLectureGroups(List<StudyingGroup> lectureGroups) {
        this.lectureGroups = lectureGroups;
    }
}

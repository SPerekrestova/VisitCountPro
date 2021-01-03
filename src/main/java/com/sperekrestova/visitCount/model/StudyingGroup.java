package com.sperekrestova.visitCount.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Svetlana
 * Date: 02.01.2021
 */
@Entity
public class StudyingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String groupName;

    @OneToMany(mappedBy="group")
    private List<Student> students;

    public StudyingGroup() {
    }

    public StudyingGroup(String groupName, List<Student> students) {
        this.groupName = groupName;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

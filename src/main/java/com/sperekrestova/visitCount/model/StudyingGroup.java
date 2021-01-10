package com.sperekrestova.visitCount.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Svetlana
 * Date: 08.01.2021
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StudyingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    //When Group instance is saved, all students are also saved
    @OneToMany(mappedBy="group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @ManyToMany
    private List<User> profs;

    public StudyingGroup(String groupName) {
        this.groupName = groupName;
    }

    public StudyingGroup(String groupName, List<Student> students, List<User> profs) {
        this.groupName = groupName;
        this.students = students;
        this.profs = profs;
    }
}

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
public class Student {

    @Id
    private Long id;

    private String firstName;
    private String secondName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyingGroup group;
}
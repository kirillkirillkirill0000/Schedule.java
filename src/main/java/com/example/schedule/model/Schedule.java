// Schedule.java
package com.example.schedule.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startLessonTime;
    private String endLessonTime;
    private String lessonTypeAbbrev;
    private String subjectFullName;

    @ManyToMany
    @JoinTable(
            name = "schedule_student_group",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "student_group_id")
    )
    private List<StudentGroup> studentGroups = new ArrayList<>();
}
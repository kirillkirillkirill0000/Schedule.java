package com.example.schedule.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Класс, представляющий расписание
@Data
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Schedule {

    // Уникальный идентификатор расписания
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Время начала занятия
    private String startLessonTime;

    // Время окончания занятия
    private String endLessonTime;

    // Сокращение типа занятия
    private String lessonTypeAbbrev;

    // Полное название предмета
    private String subjectFullName;

    // Связь многие-ко-многим с группами студентов
    @ManyToMany
    @JoinTable(
            name = "schedule_student_group",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "student_group_id")
    )
    private List<StudentGroup> studentGroups = new ArrayList<>(); // Список групп студентов
}
package com.example.schedule.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Класс, представляющий группу студентов
@Data
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StudentGroup {

    //  идентификатор группы студентов
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название специальности группы
    private String specialityName;

    // Название группы студентов
    private String name;

    // Связь многие-ко-многим с расписанием
    @ManyToMany(mappedBy = "studentGroups")
    private List<Schedule> schedules = new ArrayList<>();
}
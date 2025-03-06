package com.example.schedule.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentGroupApiResponse {
    // Геттеры и сеттеры
    private Long id;
    private String specialityName; // полное название группы
    private String name; //номер

}
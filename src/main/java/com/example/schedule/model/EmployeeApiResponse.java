package com.example.schedule.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeApiResponse {
    // Геттеры и сеттеры
    private Long id;
    private String firstName;
    private String middleName; // Отчество сотрудника
    private String lastName; // Фамилия сотрудника

}

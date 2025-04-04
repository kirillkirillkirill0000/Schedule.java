package com.example.schedule.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeApiResponse {

    private Long id;
    private String firstName;
    private String middleName;   // Отчество сотрудника
    private String lastName;   // Фамилия сотрудника

}

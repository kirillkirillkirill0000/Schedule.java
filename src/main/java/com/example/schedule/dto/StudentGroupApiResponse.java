package com.example.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentGroupApiResponse {

    private Long id;

    private String specialityName; // полное название группы

    private String name; //номер

}
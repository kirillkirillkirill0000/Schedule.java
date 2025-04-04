package com.example.schedule.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ScheduleApiResponse {

    private Map<String, List<Schedule>> schedules;

    @Setter
    @Getter
    public static class Schedule {
        private Long id;
        private List<String> auditories;
        private String startLessonTime;
        private String endLessonTime;
        private String lessonTypeAbbrev; // Сокращение типа занятия
        private String subjectFullName; // Полное название предмета
        private List<StudentGroupApiResponse> studentGroups;
        private List<EmployeeApiResponse> employees;

    }

}
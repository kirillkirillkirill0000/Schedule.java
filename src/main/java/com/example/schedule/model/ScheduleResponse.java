package com.example.schedule.model;


import java.util.List;
import java.util.Map;


public class ScheduleResponse {
    private String startDate;
    private String endDate;
    private String startExamsDate;
    private String endExamsDate;
    private Map<String, List<Schedule>> schedules;

    public ScheduleResponse() {
        // Конструктор без параметров
    }

    public Map<String, List<Schedule>> getSchedules() {
        return schedules;
    }

    public void setSchedules(Map<String, List<Schedule>> schedules) {
        this.schedules = schedules;
    }

    // Добавьте геттеры и сеттеры для других полей, если необходимо
}

package com.example.schedule.model;

import java.util.List;
import java.util.Map;

public class ScheduleResponse {
    private String startDate;
    private String endDate;
    private String startExamsDate;
    private String endExamsDate;
    private Map<String, List<Schedule>> schedules; // Карта ключ - день недели, значение - список расписаний

    public ScheduleResponse() {

    }

    public Map<String, List<Schedule>> getSchedules() {
        return schedules; // Получить расписания
    }

    public void setSchedules(Map<String, List<Schedule>> schedules) {
        this.schedules = schedules; // Установить расписания
    }
}
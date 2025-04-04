package com.example.schedule.controller;

import com.example.schedule.dao.ScheduleApiClientRepository;
import com.example.schedule.model.ScheduleApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ScheduleApiClientController {


    @Autowired
    private ScheduleApiClientRepository scheduleApiClient;

    @GetMapping("/api/schedule")
    public List<ScheduleApiResponse.Schedule> getSchedule(@RequestParam String group, @RequestParam String date) {
        // Возвращает расписание для указанной группы и даты, полученное через репозиторий
        return scheduleApiClient.getSchedule(group, date);
    }
}
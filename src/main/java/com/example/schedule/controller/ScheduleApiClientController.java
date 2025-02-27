package com.example.schedule.controller;

import com.example.schedule.dao.ScheduleApiClientRepository;
import com.example.schedule.model.ScheduleApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Контроллер для обработки запросов к API расписания
@RestController
public class ScheduleApiClientController {

    // Репозиторий для взаимодействия
    @Autowired
    private ScheduleApiClientRepository scheduleApiClient;

    // Обработчик GET-запроса для получения расписания по группе и дате
    @GetMapping("/api/schedule")
    public List<ScheduleApiResponse.Schedule> getSchedule(@RequestParam String group, @RequestParam String date) {
        // Возвращает расписание для указанной группы и даты, полученное через репозиторий
        return scheduleApiClient.getSchedule(group, date);
    }
}
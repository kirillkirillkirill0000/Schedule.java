package com.example.schedule.service;

import com.example.schedule.dao.ScheduleApiClientRepository;
import com.example.schedule.model.ScheduleApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Сервис для работы с расписанием
@Service
public class ScheduleApiService {


    @Autowired
    private ScheduleApiClientRepository scheduleApiClient;

    // Метод для получения расписания по группе и дате
    public List<ScheduleApiResponse.Schedule> getSchedule(String group, String date) {
        // Вызов метода репозитория для получения расписания
        return scheduleApiClient.getSchedule(group, date);
    }
}
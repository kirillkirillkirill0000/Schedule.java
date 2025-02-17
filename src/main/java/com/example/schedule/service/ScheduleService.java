package com.example.schedule.service;

import com.example.schedule.dao.ScheduleDAO;
import com.example.schedule.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleDAO scheduleDAO;

    public List<Schedule> getSchedule(String group, String date) {
        // для получения расписания по группе и дате
        return scheduleDAO.getSchedule(group, date); // Вызывает метод DAO для получения расписания
    }
}
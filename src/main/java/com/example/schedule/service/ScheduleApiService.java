package com.example.schedule.service;

import com.example.schedule.dao.ScheduleApiClientRepository;
import com.example.schedule.dto.ScheduleApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleApiService {

    @Autowired
    private ScheduleApiClientRepository scheduleApiClient;

    public List<ScheduleApiResponse.Schedule> getSchedule(String group, String date) {
        return scheduleApiClient.getSchedule(group, date);
    }
}
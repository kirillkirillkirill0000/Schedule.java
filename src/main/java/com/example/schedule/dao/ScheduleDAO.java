package com.example.schedule.dao;

import com.example.schedule.model.Schedule;
import com.example.schedule.model.ScheduleResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class ScheduleDAO {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Schedule> getSchedule(String group, String date) {
        String url = "https://iis.bsuir.by/api/v1/schedule?studentGroup=" + group + "&date=" + date;
        ScheduleResponse response = restTemplate.getForObject(url, ScheduleResponse.class);
        String dayOfWeek = getDayOfWeek(date);
        return response.getSchedules().get(dayOfWeek);
    }

    private String getDayOfWeek(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date parsedDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    return "Воскресенье";
                case Calendar.MONDAY:
                    return "Понедельник";
                case Calendar.TUESDAY:
                    return "Вторник";
                case Calendar.WEDNESDAY:
                    return "Среда";
                case Calendar.THURSDAY:
                    return "Четверг";
                case Calendar.FRIDAY:
                    return "Пятница";
                case Calendar.SATURDAY:
                    return "Суббота";
                default:
                    return "Unknown";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}

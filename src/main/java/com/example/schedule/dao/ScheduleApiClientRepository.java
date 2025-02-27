package com.example.schedule.dao; // Data Access Object для работы с расписанием через API

import com.example.schedule.model.ScheduleApiResponse;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Repository
public class ScheduleApiClientRepository {

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate для выполнения запросов

    public List<ScheduleApiResponse.Schedule> getSchedule(String group, String date) {
        String url = "https://iis.bsuir.by/api/v1/schedule?studentGroup=" + group + "&date=" + date;

        ScheduleApiResponse response = restTemplate.getForObject(url, ScheduleApiResponse.class);

        // Получаем день недели для заданной даты
        String dayOfWeek = getDayOfWeek(date);

        // Возвращаем расписание для соответствующего дня недели
        return response != null && response.getSchedules() != null ?
                response.getSchedules().getOrDefault(dayOfWeek, List.of()) : List.of();
    }

    private String getDayOfWeek(String date) {
        // Метод для получения названия дня недели по заданной дате
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); // Формат даты
            Date parsedDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance(); // Создаем объект Calendar
            calendar.setTime(parsedDate); // Устанавливаем дату в календарь
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Получаем день недели

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
                    return "Unknown"; // Если не распознан
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
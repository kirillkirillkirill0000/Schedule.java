package com.example.schedule.dao; //  Data Access Object для работы с расписанием

import com.example.schedule.model.Schedule;     //  представляющей расписание
import com.example.schedule.model.ScheduleResponse;      // представляющей ответ от API
import org.springframework.stereotype.Repository;          //  определения класса как репозитория
import org.springframework.web.client.RestTemplate;  //класса для выполнения HTTP-запросов

import java.text.SimpleDateFormat;        // для работы с форматированием даты
import java.util.Calendar;  //  для работы с календарем
import java.util.Date;             //  для работы с датами
import java.util.List;      // List для работы со списками
import java.util.Locale;

@Repository                                // класс является репозиторием
public class ScheduleDAO {

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate для выполнения запросов

    public List<Schedule> getSchedule(String group, String date) {

        String url = "https://iis.bsuir.by/api/v1/schedule?studentGroup=" + group + "&date=" + date;


        ScheduleResponse response = restTemplate.getForObject(url, ScheduleResponse.class);

        // Получаем день недели для заданной даты
        String dayOfWeek = getDayOfWeek(date);

        // Возвращаем расписание для соответствующего дня недели
        return response.getSchedules().get(dayOfWeek);
    }

    private String getDayOfWeek(String date) {
        // метод для получения названия дня недели по заданной дате
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH); // формат даты
            Date parsedDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();   // создаем объект Calendar
            calendar.setTime(parsedDate); //  дату в календарь
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);    // Получаем день недели

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
                    return "Unknown"; // если не распознан
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
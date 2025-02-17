package com.example.schedule.controller;          //  контроллер для работы с расписанием

import com.example.schedule.model.Schedule;                         //   представляющего расписание
import com.example.schedule.service.ScheduleService;    // для работы с расписанием
import org.springframework.beans.factory.annotation.Autowired;           // для автоматического связывания зависимостей
import org.springframework.web.bind.annotation.GetMapping;       //  для обработки GET-запросов
import org.springframework.web.bind.annotation.RequestParam;  //  для получения параметров запроса из URL
import org.springframework.web.bind.annotation.RestController;   // для создания REST-контроллера

import java.util.List;      // для работы со списками расписаний

@RestController            // класс является REST-контроллером и будет обрабатывать HTTP-запросы
public class ScheduleController {

    @Autowired            //  внедрен экземпляр ScheduleService в контроллер
    private ScheduleService scheduleService; // Сервис для получения расписаний

    @GetMapping("/schedule")     // Обрабатывает GET-запросы по адресу /schedule
    public List<Schedule> getSchedule(@RequestParam String group, @RequestParam String date) {
        // Метод для получения расписания по группе и дате
        // Параметры group и date извлекаются из URL запроса

        return scheduleService.getSchedule(group, date); // Вызывает метод сервиса для получения расписания
    }
}
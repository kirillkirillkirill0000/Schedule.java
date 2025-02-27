package com.example.schedule.controller;

import com.example.schedule.model.Schedule;
import com.example.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Контроллер для управления расписаниями
@RestController
@RequestMapping("/schedules")
public class ScheduleController {


    private final ScheduleService scheduleService;

    // Конструктор для внедрения зависимости
    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // создания нового расписания
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        try {
            // Сохранение расписания и возвращение его в ответе
            Schedule savedSchedule = scheduleService.save(schedule);
            return ResponseEntity.ok(savedSchedule);
        } catch (Exception e) {
            // Обработка ошибок при сохранении
            return ResponseEntity.status(500).body(null);
        }
    }

    //  получения списка всех расписаний
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }

    // получения расписания по ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.findById(id)
                .map(ResponseEntity::ok) // Возвращение расписания, если найдено
                .orElse(ResponseEntity.notFound().build()); // Возвращение 404, если не найдено
    }

    //  обновления существующего расписания
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        return scheduleService.findById(id)
                .map(schedule -> {
                    // Обновление полей расписания
                    schedule.setStartLessonTime(scheduleDetails.getStartLessonTime());
                    schedule.setEndLessonTime(scheduleDetails.getEndLessonTime());
                    schedule.setLessonTypeAbbrev(scheduleDetails.getLessonTypeAbbrev());
                    schedule.setSubjectFullName(scheduleDetails.getSubjectFullName());
                    // Сохранение обновленного расписания
                    Schedule updatedSchedule = scheduleService.save(schedule);
                    return ResponseEntity.ok(updatedSchedule); // Возвращение обновленного расписания
                })
                .orElse(ResponseEntity.notFound().build()); //  если не найдено
    }

    //  удаления расписания по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id); // Удаление расписания
        return ResponseEntity.noContent().build(); // Возвращение 204 No Content
    }
}
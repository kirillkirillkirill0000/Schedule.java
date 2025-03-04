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


    @Autowired // Конструктор для внедрения зависимости
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping// создания нового расписания
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


    @GetMapping //  получения списка всех расписаний
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }


    @GetMapping("/{id}")// получения расписания по ID
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.findById(id)
                .map(ResponseEntity::ok) // Возвращение расписания, если найдено
                .orElse(ResponseEntity.notFound().build()); // Возвращение 404, если не найдено
    }

    @PutMapping("/update")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule scheduleDetails) {
        if (scheduleDetails.getId() == null) {
            return ResponseEntity.badRequest().build(); // Возвращаем 400, если ID не указан
        }

        Schedule updatedSchedule = scheduleService.update(scheduleDetails.getId(), scheduleDetails);
        return ResponseEntity.ok(updatedSchedule); // Возвращаем обновленное расписание
    }


    @DeleteMapping("/{id}")//  удаления расписания по ID
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id); // Удаление расписания
        return ResponseEntity.noContent().build(); // Возвращение 204 нет контента
    }
}
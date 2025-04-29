package com.example.schedule.controller;

import com.example.schedule.model.Schedule;
import com.example.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Получить все расписания
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }

    // Получить расписание по ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.findById(id);
        return schedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Получить расписания по ID группы
    @GetMapping("/by-group/{groupId}")
    public List<Schedule> getSchedulesByGroupId(@PathVariable Long groupId) {
        return scheduleService.findByGroupId(groupId);
    }

    // Создать новое расписание
    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.save(schedule);
    }

    // Обновить расписание
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        Schedule updatedSchedule = scheduleService.update(id, scheduleDetails);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Удалить расписание
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Получить расписания по типу занятия и предмету
    @GetMapping("/by-lesson-type-and-subject")
    public List<Schedule> getSchedulesByLessonTypeAndSubject(
            @RequestParam String lessonTypeAbbrev,
            @RequestParam String subjectFullName) {
        return scheduleService.findByLessonTypeAndSubject(lessonTypeAbbrev, subjectFullName);
    }
}
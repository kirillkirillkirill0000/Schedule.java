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

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.findById(id);
        return schedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-group/{groupId}")
    public List<Schedule> getSchedulesByGroupId(@PathVariable Long groupId) {
        return scheduleService.findByGroupId(groupId);
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.save(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule scheduleDetails) {
        Schedule updatedSchedule = scheduleService.update(id, scheduleDetails);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by-lesson-type-and-subject")
    public List<Schedule> getSchedulesByLessonTypeAndSubject(
            @RequestParam String lessonTypeAbbrev,
            @RequestParam String subjectFullName) {
        return scheduleService.findByLessonTypeAndSubject(lessonTypeAbbrev, subjectFullName);
    }
}
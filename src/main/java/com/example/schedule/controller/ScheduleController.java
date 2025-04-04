package com.example.schedule.controller;

import com.example.schedule.model.Schedule;
import com.example.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schedules")
public class ScheduleController {


    private final ScheduleService scheduleService;


    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        try {

            Schedule savedSchedule = scheduleService.save(schedule);
            return ResponseEntity.ok(savedSchedule);
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/by-type-and-subject")
    public List<Schedule> getSchedulesByTypeAndSubject(
            @RequestParam String lessonTypeAbbrev,
            @RequestParam String subjectFullName) {
        return scheduleService.findByLessonTypeAndSubject(lessonTypeAbbrev, subjectFullName);
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        return scheduleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule scheduleDetails) {
        if (scheduleDetails.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Schedule updatedSchedule = scheduleService.update(scheduleDetails.getId(), scheduleDetails);
        return ResponseEntity.ok(updatedSchedule);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
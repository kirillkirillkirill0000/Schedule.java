package com.example.schedule.service;

import com.example.schedule.Cache.ScheduleCache;
import com.example.schedule.dao.ScheduleRepository;
import com.example.schedule.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleCache scheduleCache;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleCache scheduleCache) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleCache = scheduleCache;
    }

    public List<Schedule> findAll() {
        return scheduleCache.getAll();
    }

    public Optional<Schedule> findById(Long id) {

        Schedule cachedSchedule = scheduleCache.get(id);
        if (cachedSchedule != null) {
            return Optional.of(cachedSchedule);
        }
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        schedule.ifPresent(s -> scheduleCache.put(id, s));
        return schedule;
    }

    public Schedule save(Schedule schedule) {
        Schedule savedSchedule = scheduleRepository.save(schedule);
        scheduleCache.put(savedSchedule.getId(), savedSchedule);
        return savedSchedule;
    }

    public Schedule update(Long id, Schedule scheduleDetails) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));
        schedule.setStartLessonTime(scheduleDetails.getStartLessonTime());
        schedule.setEndLessonTime(scheduleDetails.getEndLessonTime());
        schedule.setLessonTypeAbbrev(scheduleDetails.getLessonTypeAbbrev());
        schedule.setSubjectFullName(scheduleDetails.getSubjectFullName());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        scheduleCache.put(id, updatedSchedule); // Обновление кэша
        return updatedSchedule;
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
        scheduleCache.invalidate(id);
    }

    public List<Schedule> findByLessonTypeAndSubject(String lessonTypeAbbrev, String subjectFullName) {
        return scheduleRepository.findByLessonTypeAndSubjectFullName(lessonTypeAbbrev, subjectFullName);
    }
}
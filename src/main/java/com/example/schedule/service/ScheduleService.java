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
        List<Schedule> cached = scheduleCache.getAll();
        if (cached != null && !cached.isEmpty()) {
            return cached;
        }
        List<Schedule> schedules = scheduleRepository.findAll();
        scheduleCache.putAll(schedules);
        return schedules;
    }

    public Optional<Schedule> findById(Long id) {
        Schedule cached = scheduleCache.get(id);
        if (cached != null) {
            return Optional.of(cached);
        }
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        schedule.ifPresent(s -> scheduleCache.put(s.getId(), s));
        return schedule;
    }

    public Schedule save(Schedule schedule) {
        Schedule saved = scheduleRepository.save(schedule);
        scheduleCache.put(saved.getId(), saved);
        return saved;
    }

    public Schedule update(Long id, Schedule scheduleDetails) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));
        schedule.setStartLessonTime(scheduleDetails.getStartLessonTime());
        schedule.setEndLessonTime(scheduleDetails.getEndLessonTime());
        schedule.setLessonTypeAbbrev(scheduleDetails.getLessonTypeAbbrev());
        schedule.setSubjectFullName(scheduleDetails.getSubjectFullName());
        Schedule updated = scheduleRepository.save(schedule);
        scheduleCache.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        scheduleRepository.deleteById(id);
        scheduleCache.invalidate(id);
    }

    public List<Schedule> findByLessonTypeAndSubject(String lessonTypeAbbrev, String subjectFullName) {
        String cacheKey = lessonTypeAbbrev + ":" + subjectFullName;
        List<Schedule> cached = scheduleCache.getByCustomKey(cacheKey);
        if (cached != null) {
            return cached;
        }
        List<Schedule> schedules = scheduleRepository
                .findByLessonTypeAndSubjectFullName(lessonTypeAbbrev, subjectFullName);
        scheduleCache.putByCustomKey(cacheKey, schedules);
        return schedules;
    }
}
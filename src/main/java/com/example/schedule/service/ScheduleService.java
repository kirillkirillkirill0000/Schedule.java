package com.example.schedule.service;

import com.example.schedule.dao.ScheduleRepository;
import com.example.schedule.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Сервис для работы с расписанием
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //  для внедрения зависимости
    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

       // Метод для получения всех расписаний
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

     // для поиска расписания по идентификатору
    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    // Метод для сохранения нового расписания
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // обновления существующего расписания
    public Schedule update(Long id, Schedule scheduleDetails) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id " + id));
        // Обновление полей расписания
        schedule.setStartLessonTime(scheduleDetails.getStartLessonTime());
        schedule.setEndLessonTime(scheduleDetails.getEndLessonTime());
        schedule.setLessonTypeAbbrev(scheduleDetails.getLessonTypeAbbrev());
        schedule.setSubjectFullName(scheduleDetails.getSubjectFullName());
        return scheduleRepository.save(schedule); // Сохранение обновленного расписания
    }

    // удаления расписания по идентификатору
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }
}
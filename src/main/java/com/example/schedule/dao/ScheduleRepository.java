package com.example.schedule.dao;

import com.example.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Репозиторий для работы с сущностью Schedule
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // JpaRepository предоставляет стандартные CRUD операции
}
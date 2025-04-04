package com.example.schedule.dao;

import com.example.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.lessonTypeAbbrev = :lessonTypeAbbrev AND s.subjectFullName = :subjectFullName")
    List<Schedule> findByLessonTypeAndSubjectFullName(
            @Param("lessonTypeAbbrev") String lessonTypeAbbrev,
            @Param("subjectFullName") String subjectFullName);
}
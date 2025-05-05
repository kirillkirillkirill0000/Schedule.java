package com.example.schedule.dao;

import com.example.schedule.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.lessonTypeAbbrev = :lessonType AND s.subjectFullName = :subject")
    List<Schedule> findByLessonTypeAndSubjectFullName(
            @Param("lessonType") String lessonTypeAbbrev,
            @Param("subject") String subjectFullName
    );

    @Query("SELECT s FROM Schedule s JOIN s.studentGroups sg WHERE sg.id = :groupId")
    List<Schedule> findByGroupId(@Param("groupId") Long groupId);
}
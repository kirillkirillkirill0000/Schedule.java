package com.example.schedule.model;

import java.util.List;
import java.util.Map;

//  для представления ответа API с расписанием
public class ScheduleApiResponse {


    private Map<String, List<Schedule>> schedules;

    // Вложенный класс для расписания с нужными полями
    public static class Schedule {
        private Long id;
        private List<String> auditories;
        private String startLessonTime;
        private String endLessonTime;
        private String lessonTypeAbbrev; // Сокращение типа занятия
        private String subjectFullName; // Полное название предмета
        private List<StudentGroup> studentGroups;
        private List<Employee> employees;


        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public List<String> getAuditories() { return auditories; }
        public void setAuditories(List<String> auditories) { this.auditories = auditories; }
        public String getStartLessonTime() { return startLessonTime; }
        public void setStartLessonTime(String startLessonTime) { this.startLessonTime = startLessonTime; }
        public String getEndLessonTime() { return endLessonTime; }
        public void setEndLessonTime(String endLessonTime) { this.endLessonTime = endLessonTime; }
        public String getLessonTypeAbbrev() { return lessonTypeAbbrev; }
        public void setLessonTypeAbbrev(String lessonTypeAbbrev) { this.lessonTypeAbbrev = lessonTypeAbbrev; }
        public String getSubjectFullName() { return subjectFullName; }
        public void setSubjectFullName(String subjectFullName) { this.subjectFullName = subjectFullName; }
        public List<StudentGroup> getStudentGroups() { return studentGroups; }
        public void setStudentGroups(List<StudentGroup> studentGroups) { this.studentGroups = studentGroups; }
        public List<Employee> getEmployees() { return employees; }
        public void setEmployees(List<Employee> employees) { this.employees = employees; }
    }

    // Вложенный класс для студенческой группы
    public static class StudentGroup {
        private Long id;
        private String specialityName;
        private String name;


        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getSpecialityName() { return specialityName; }
        public void setSpecialityName(String specialityName) { this.specialityName = specialityName; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    // Вложенный класс для сотрудника
    public static class Employee {
        private Long id;
        private String firstName;
        private String middleName; // Отчество сотрудника
        private String lastName; // Фамилия сотрудника


        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getMiddleName() { return middleName; }
        public void setMiddleName(String middleName) { this.middleName = middleName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }


    public Map<String, List<Schedule>> getSchedules() { return schedules; }
    public void setSchedules(Map<String, List<Schedule>> schedules) { this.schedules = schedules; }
}
package com.example.schedule.model;

import java.util.List;

public class Schedule {
    private List<String> auditories;
    private String endLessonTime;
    private String lessonTypeAbbrev;
    private String note;
    private int numSubgroup;
    private String startLessonTime;
    private List<StudentGroup> studentGroups;
    private String subject;
    private String subjectFullName;
    private List<Integer> weekNumber;
    private List<Employee> employees;
    private String dateLesson;
    private String startLessonDate;
    private String endLessonDate;
    private boolean announcement;
    private boolean split;

    public Schedule() {
        // Конструктор без параметров
    }

    // Getters and setters
    public List<String> getAuditories() {
        return auditories;
    }

    public void setAuditories(List<String> auditories) {
        this.auditories = auditories;
    }

    public String getEndLessonTime() {
        return endLessonTime;
    }

    public void setEndLessonTime(String endLessonTime) {
        this.endLessonTime = endLessonTime;
    }

    public String getLessonTypeAbbrev() {
        return lessonTypeAbbrev;
    }

    public void setLessonTypeAbbrev(String lessonTypeAbbrev) {
        this.lessonTypeAbbrev = lessonTypeAbbrev;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNumSubgroup() {
        return numSubgroup;
    }

    public void setNumSubgroup(int numSubgroup) {
        this.numSubgroup = numSubgroup;
    }

    public String getStartLessonTime() {
        return startLessonTime;
    }

    public void setStartLessonTime(String startLessonTime) {
        this.startLessonTime = startLessonTime;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectFullName() {
        return subjectFullName;
    }

    public void setSubjectFullName(String subjectFullName) {
        this.subjectFullName = subjectFullName;
    }

    public List<Integer> getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(List<Integer> weekNumber) {
        this.weekNumber = weekNumber;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getDateLesson() {
        return dateLesson;
    }

    public void setDateLesson(String dateLesson) {
        this.dateLesson = dateLesson;
    }

    public String getStartLessonDate() {
        return startLessonDate;
    }

    public void setStartLessonDate(String startLessonDate) {
        this.startLessonDate = startLessonDate;
    }

    public String getEndLessonDate() {
        return endLessonDate;
    }

    public void setEndLessonDate(String endLessonDate) {
        this.endLessonDate = endLessonDate;
    }

    public boolean isAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(boolean announcement) {
        this.announcement = announcement;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }
}

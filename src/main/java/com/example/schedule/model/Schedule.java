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

    }


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
        return lessonTypeAbbrev;      //  аббревиатура типа занятия
    }

    public void setLessonTypeAbbrev(String lessonTypeAbbrev) {
        this.lessonTypeAbbrev = lessonTypeAbbrev;
    }

    public String getNote() {
        return note;      //  примечание
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNumSubgroup() {
        return numSubgroup;     // номер подгруппы
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
        this.weekNumber = weekNumber;         //  номера недель
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;       //  список сотрудников
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
        this.announcement = announcement;      //  флаг объявления
    }

    public boolean isSplit() {
        return split;      //  разделения занятий
    }

    public void setSplit(boolean split) {
        this.split = split;
    }
}
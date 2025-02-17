package com.example.schedule.model;

public class StudentGroup {
    private String specialityName;
    private String specialityCode;
    private int numberOfStudents; //кол-во студентов
    private String name;
    private int educationDegree;

    public StudentGroup() {

    }

    // Getters and setters
    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpecialityCode() {
        return specialityCode; //  код специальности
    }

    public void setSpecialityCode(String specialityCode) {
        this.specialityCode = specialityCode;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;   // количество студентов
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(int educationDegree) {
        this.educationDegree = educationDegree;     // уровень образования
    }
}
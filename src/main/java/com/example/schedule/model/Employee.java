package com.example.schedule.model;

public class Employee {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String photoLink;
    private String degree;
    private String degreeAbbrev;
    private String rank;
    private String email;
    private String urlId;
    private String calendarId;
    private String jobPositions;

    public Employee() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName; // Получить имя
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName; // Установить имя
    }

    public String getMiddleName() {
        return middleName; // Получить отчество
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName; // Установить отчество
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;     // Аббревиатура степени
    }

    public String getDegreeAbbrev() {
        return degreeAbbrev;
    }

    public void setDegreeAbbrev(String degreeAbbrev) {
        this.degreeAbbrev = degreeAbbrev;
    }

    public String getRank() {
        return rank;       // Звание
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getJobPositions() {
        return jobPositions;
    }

    public void setJobPositions(String jobPositions) {
        this.jobPositions = jobPositions;   //  установка должн
    }
}
package com.team3.getjob;

public class job_model {

    private String title;
    private String description;
    private String location;
    private int year;

    public job_model(String title, String description, String location, int year) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
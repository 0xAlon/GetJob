package com.team3.getjob;

import java.util.ArrayList;
import java.util.Date;

public class job_model {

    private String title;
    private String description;
    private String location;
    private String payment;
    private String rank;
    private boolean ageAdult;
    private Date date;
    private ArrayList<String> languages;
    private ArrayList<String> users;

    public job_model() { }

    public job_model(String title, String description, String location, String payment, String rank, boolean ageAdult, Date date, ArrayList<String> languages, ArrayList<String> users) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.payment = payment;
        this.rank = rank;
        this.ageAdult = ageAdult;
        this.date = date;
        this.languages = languages;
        this.users = users;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean isAgeAdult() {
        return ageAdult;
    }

    public void setAgeAdult(boolean ageAdult) {
        this.ageAdult = ageAdult;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}

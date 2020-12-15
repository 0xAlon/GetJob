package com.team3.getjob;

import java.util.ArrayList;
import java.util.Date;

public class job_model {

    private String title;
    private String description;
    private String location;
    private String payment;
    private int rank;
    private Date date;
    private ArrayList<String> languages;

    public job_model(String title, String description, String location, String payment, int rank, Date date, ArrayList<String> languages) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.payment = payment;
        this.rank = rank;
        this.date = date;
        this.languages = languages;
    }


    public job_model() {
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

    public double getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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



}
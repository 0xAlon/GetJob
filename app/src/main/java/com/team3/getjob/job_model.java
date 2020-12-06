package com.team3.getjob;

import java.lang.reflect.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;

public class job_model {

    private String title;
    private String description;
    private String location;
    private String payment;
    private String rank;
    private Date date;
    private ArrayList<String> languages;

    public job_model(String title, String description, String location, String payment, String rank, Date date, ArrayList<String> languages) {
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
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

    /*//add data and auto generate id+++++++++++++++++++++++++
        temp_lang.add("English");
        Date temp_date = new Date();

        job_model jobs = new job_model("PHP Developer",
                "Smart and impressive man for work in industry",
                "Los angeles", 300, 5, temp_date,temp_lang);

            db.collection("Posts")
                    .add(jobs)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("check", "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("check", "Error adding document", e);
                        }
                    });*/

}

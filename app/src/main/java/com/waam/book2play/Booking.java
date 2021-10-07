package com.waam.book2play;

public class Booking {
    private String time;
    private String date;
    private String email;
    private String stadiumName;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //Default Constructor
    public Booking() {
    }
    //Paramterized Constructor
    public Booking(String time, String date, String email, String stadiumName) {
        this.time = time;
        this.date = date;
        this.email = email;
        this.stadiumName = stadiumName;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}

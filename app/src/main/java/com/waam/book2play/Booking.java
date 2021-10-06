package com.waam.book2play;

public class Booking {
    private String time;
    private String date;
    private String email;

    //Default Constructor
    public Booking() {
    }
    //Paramterized Constructor
    public Booking(String time, String date, String email) {
        this.time = time;
        this.date = date;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Getters
    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
    //Setters
    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

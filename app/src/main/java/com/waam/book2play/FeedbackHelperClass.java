package com.waam.book2play;

//to add feedback we can use this helper class which will store data in feedback
public class FeedbackHelperClass {

    //variables need to insert to database
    String email,feedback;
    Float rating;

    //for fire base to avoid error we need to implement an empty constructor
    public FeedbackHelperClass() {
    }

    //constructors


    public FeedbackHelperClass(String email, String feedback, Float rating) {
        this.email = email;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}

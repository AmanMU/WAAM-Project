package com.waam.book2play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedback_view extends AppCompatActivity {

    //variables
    TextInputLayout name, feedback;
    RatingBar ratingBar;

    //Global variables to hold user data inside this activity
    String input_name, input_feedback;
    Float input_rating;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        reference = FirebaseDatabase.getInstance().getReference("Feedback");


        //hooks
        name = findViewById(R.id.nameview);
        feedback = findViewById(R.id.feedbackview);
        ratingBar=findViewById(R.id.ratingview);

        //show all data
        showAllData();


    }

    private void showAllData() {
        Intent intent = getIntent();
        input_name = intent.getStringExtra("name");
        input_feedback = intent.getStringExtra("feedback");
        input_rating=intent.getFloatExtra("rating",0);

        name.getEditText().setText(input_name);
        feedback.getEditText().setText(input_feedback);
        ratingBar.setRating(input_rating);



    }

    public void update(View view) {
        if (isNameChanged() || isFeedbackChanged()) {
            Toast.makeText(this, "Data successfully updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data entered is same and cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNameChanged() {
        //checking whether the update inputted name is not equal
        if (!input_name.equals(name.getEditText().getText().toString())) {
            //if its not equal
            reference.child(input_name).child("name").setValue(name.getEditText().getText().toString());
            input_name = name.getEditText().getText().toString();
            return true;

        } else {
            return false;
        }

    }


    private boolean isFeedbackChanged() {
        //checking whether the update inputted name is not equal
        if (!input_feedback.equals(feedback.getEditText().getText().toString())) {
            //if its not equal
            reference.child(input_name).child("feedback").setValue(feedback.getEditText().getText().toString());
            input_feedback = feedback.getEditText().getText().toString();
            return true;

        } else {
            return false;
        }


    }
}
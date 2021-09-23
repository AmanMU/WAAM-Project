package com.waam.book2play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class feedback_rating extends AppCompatActivity {
    //variables
    TextInputLayout feed_back, feedback_name;
    Button btn_Rate;
//    TextView tv_Rating;
   RatingBar ratingBar;


    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseUser user;



    //validating the input fields
    private Boolean validateName() {

        //get the inputted value
        String val = feedback_name.getEditText().getText().toString();

        //check is it empty
        if (val.isEmpty()) {
            feedback_name.setError("Field cannot be empty!");
            return false;
        } else {
            feedback_name.setError(null);
            return true;
        }
    }
    private Boolean validateFeedback() {

        //get the inputted value
        String val = feed_back.getEditText().getText().toString();

        //check is it empty
        if (val.isEmpty()) {
            feed_back.setError("Field cannot be empty!");
            return false;
        } else {
            feed_back.setError(null);
            return true;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_rating);



        //hooks to all xml elements in the Activity_feedback_rating.xml

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btn_Rate = findViewById(R.id.btn_Rate);
        feed_back = findViewById(R.id.feedbackinput);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String useremail=user.getEmail();


        Float ratingNumber = ratingBar.getRating(); // get rating number from a rating bar
        int numberOfStars = ratingBar.getNumStars(); // get total number of stars of rating bar

        //save data in the fireBase on button click
        btn_Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateFeedback() | !validateName() ){
                    return ;
                   }else{

                    isFeedback();

                }



                //use to state the path of the reference in the database,which one should called
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Feedback");

                //get all the values
                String feedback = feed_back.getEditText().getText().toString();
               Float rating = ratingBar.getRating();



                //call feedbackhelper class,inside the constructor pass all the values which came from the inputted field
                FeedbackHelperClass helperClass = new FeedbackHelperClass(useremail, feedback,"ID1234",rating);

                //to set values inside the feedback
                String id=reference.push().getKey();
                reference.child(id).setValue(helperClass);

            }

            private void isFeedback() {

                //get the user entered values inside string
                String userEnteredName = feedback_name.getEditText().getText().toString().trim();
                String userEnteredFeedback=feed_back.getEditText().getText().toString().trim();
                Float userEnteredRating=ratingBar.getRating();
                String userEnteredRatingString=String.valueOf(userEnteredRating);

                //to work on firebase
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Feedback");

                //check for the name inside the feedback on the firebase and match it to the entered value
                Query checkFeedback=reference.orderByChild("name").equalTo(userEnteredName);

                checkFeedback.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check whether data is inside this data snapshot
                        if(dataSnapshot.exists()){


                            feedback_name.setError(null);
                            feedback_name.setErrorEnabled(false);
                            //get data if available
                            String feedbackFromDB=dataSnapshot.child(userEnteredName).child("feedback").getValue(String.class);

                            if(feedbackFromDB.equals(userEnteredFeedback)){

                                feedback_name.setError(null);
                                feedback_name.setErrorEnabled(false);

                                String nameFromDB=dataSnapshot.child(userEnteredName).child("name").getValue(String.class);
                                Float ratingFromDB=dataSnapshot.child(userEnteredName).child("rating").getValue(Float.class);



                                Intent intent= new Intent(getApplicationContext(),feedback_view.class);

                                intent.putExtra("name",nameFromDB);
                                intent.putExtra("feedback",feedbackFromDB);
                                intent.putExtra("rating",ratingFromDB);


                                startActivity(intent);


                            }
                            else{
                                feedback_name.setError("Something wrong,name cannot be found");
                                feedback_name.requestFocus();
                            }
                        }

                    }

                    @Override
                    //we have the error methods here
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });


            }
        });


//        rb_Stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if (rating == 0) {
//                    tv_Rating.setText("Stars=0 Very Dissatisfied");
//                } else if (rating == 1) {
//                    tv_Rating.setText("Stars=1 Dissatisfied");
//                } else if (rating == 2) {
//                    tv_Rating.setText("Stars=2 Satisfied a bit");
//                } else if (rating == 3) {
//                    tv_Rating.setText("Stars=3 Satisfied");
//                } else if (rating == 4) {
//                    tv_Rating.setText("Stars=4 Highly Satisfied");
//                } else {
//
//                }
//
//            }
//        });
    }


//    private void testRatingBar() {
//
//         DatabaseReference reference = rootNode.getReference("Feedback").child("rating");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
//                    float rating = Float.parseFloat(dataSnapshot.getValue().toString());
//                    ratingBar.setRating(rating);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) { }
//        });
//
//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if (fromUser) reference.setValue(rating);
//            }
//        });
//
//    }
}
package com.waam.book2play;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleStadium extends AppCompatActivity {
    private ImageView sImage;
    private Toolbar toolbar;

    //variables
    TextInputLayout feed_back;
    TextView ratingcount;
    Button btn_Rate;
    RatingBar ratingBar,totalBar;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_stadium);

        //hooks to all xml elements in the Activity_feedback_rating.xml
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        totalBar=(RatingBar)findViewById(R.id.totalratingbar);
        btn_Rate = findViewById(R.id.btn_Rate);
        feed_back = findViewById(R.id.feedbackinput);
        user= FirebaseAuth.getInstance().getCurrentUser();
        ratingcount=findViewById(R.id.count);

        //created a custom hook to display thank you message
        LayoutInflater inflater=getLayoutInflater();
        View layout =inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_layout));

        final Toast toast= new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);



        Intent i = getIntent();
        StadiumRegister stadium = (StadiumRegister) i.getSerializableExtra("stadium");
        toolbar = findViewById(R.id.toolbar);
        sImage = findViewById(R.id.stadium_image);
        toolbar.setTitle(stadium.getsName());
        Picasso.get().load(stadium.getsImageURL())
                .fit()
                .centerCrop()
                .into(sImage);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);





        //use to state the path of the reference in the database,which one should called
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("stadiums").child(stadium.getsKey()).child("feedbacks");
        final String[] id = {reference.push().getKey()};

        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Float[] totalRating = {0.f};
                int total[] = {0};
                snapshot.getChildren().forEach(e->{
                    FeedbackHelperClass obj =  e.getValue(FeedbackHelperClass.class);
                    totalRating[0] += obj.getRating();
                    total[0]++;
                    if(obj.getEmail().equals(user.getEmail())) {
                        feed_back.getEditText().setText(obj.getFeedback());
                        ratingBar.setRating(obj.getRating());
                        id[0] = e.getKey();
                        return;
                    }
                });
                totalRating[0] = totalRating[0]/ total[0];
                System.out.println(totalRating[0]);
                totalBar.setRating(totalRating[0]);

                //totalratingbar.setRating(totalRating[0]);
              String num=String.valueOf(total[0]);
                 ratingcount.setText(num);


                //textinputrate.setText(total[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //save data in the fireBase on button click
        btn_Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toast.show();


                //get all the values
                String feedback = feed_back.getEditText().getText().toString();
                Float rating = ratingBar.getRating();
                String useremail=user.getEmail();


                //call feedbackhelper class,inside the constructor pass all the values which came from the inputted field
                FeedbackHelperClass helperClass = new FeedbackHelperClass(useremail, feedback,rating);

                //to set values inside the feedback

                reference.child(id[0]).setValue(helperClass);

            }


        });

    }

}
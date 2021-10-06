package com.waam.book2play;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
    DatabaseReference bookingReference;
    FirebaseUser user;

    private Button datePicker;
    private Button timePicker;
    private Button book_btn;
    private TextView selectedDate;
    private TextView selectedTime;



    //Object decleration of booking
    Booking book;

    private String timeselected;
    private String dateselected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_stadium);

        Intent i = getIntent();
        StadiumRegister stadium = (StadiumRegister) i.getSerializableExtra("stadium");

        //hooks to all xml elements in the Activity_feedback_rating.xml
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        totalBar=(RatingBar)findViewById(R.id.totalratingbar);
        btn_Rate = findViewById(R.id.btn_Rate);
        feed_back = findViewById(R.id.feedbackinput);
        user= FirebaseAuth.getInstance().getCurrentUser();
        ratingcount=findViewById(R.id.count);

        datePicker = findViewById(R.id.btn_selectdate);
        timePicker = findViewById(R.id.btn_timeselect);
        book_btn = findViewById(R.id.btn_book);
        selectedDate = findViewById(R.id.tx_date);
        selectedTime = findViewById(R.id.tx_time);





        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(today);

        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        long january = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.MARCH);
        long march = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        long december = calendar.getTimeInMillis();

        //CalendarConstraints(Validation)
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());
        //MaterialDatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE FOR WORKOUT");
        builder.setSelection(today);
        builder.setCalendarConstraints(constraintBuilder.build());
        final MaterialDatePicker materialDatePicker = builder.setTheme(R.style.ThemeOverlay_App_MaterialCalendar).build();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                selectedDate.setText("Selected Date : " + materialDatePicker.getHeaderText());
                dateselected = materialDatePicker.getHeaderText();

            }
        });


        //  Time picker
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int mins=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(SingleStadium.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar currentTime = Calendar.getInstance();




                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        String myDate;
                        if(dateselected != null){
                            myDate = dateselected + " " + time;
                        }else{
                            Toast.makeText(getApplicationContext(),"Please select a date first",Toast.LENGTH_SHORT).show();
                            return ;
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy HH:mm");
                        Date date = null;
                        try {
                            date = sdf.parse(myDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long selectedTimeInMillis = date.getTime();
                        long currentTimeInMillis = new Date().getTime();

                        Log.d("current", String.valueOf(currentTimeInMillis));
                        Log.d("selected", String.valueOf(selectedTimeInMillis));
                        //Time Validation
                        if(selectedTimeInMillis < currentTimeInMillis){
                            Toast.makeText(getApplicationContext(),"Please choose a time past current time",Toast.LENGTH_SHORT).show();
                        }else{
                            selectedTime.setText("Selected Time : " + time);
                            timeselected = time;
                        }

                    }
                },hours ,mins,false);
                timePickerDialog.show();
            }
        });

        //Method to send the data to the database
//        book_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reference = FirebaseDatabase.getInstance().getReference().child("Booking");
//
//                try{
//                    //error message when user did not select the date
//                    if(dateselected == null || dateselected.isEmpty() || dateselected.trim().isEmpty()){
//                        Toast.makeText(getApplicationContext(),"Please select a date",Toast.LENGTH_LONG).show();
//                        //error message when user did not select the time
//                    }else if(timeselected == null || timeselected.isEmpty() || timeselected.trim().isEmpty()){
//                        Toast.makeText(getApplicationContext(),"Please select a Time",Toast.LENGTH_LONG).show();
//                    }else{
//                        //set the text field when user select the time and date
//                        //book.setDate(dateselected.trim());
//                        // book.setTime(timeselected.trim());
//                        //pushing the data to the text field
//                        reference.child(getuserId()).push().setValue(book);
//                        Toast.makeText(getApplicationContext(),"Session Booked",Toast.LENGTH_LONG).show();
//                        //set text fields to default method
//                        clearControls();
//
//                    }
//                    //catch method
//                }catch(NumberFormatException e){
//                    Toast.makeText(getApplicationContext(),"An Error occured",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        
        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeselected == null || dateselected == null){
                    Toast.makeText(SingleStadium.this, "Please select date and time",Toast.LENGTH_LONG).show();
                }else {
                    bookingReference = rootNode.getReference("stadiums").child(stadium.getsKey()).child("bookings");
                    String key = dateselected + timeselected;

                    bookingReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(SingleStadium.this, "Booking already exists in the selected date and time", Toast.LENGTH_LONG).show();
                            } else {
                                book = new Booking(timeselected, dateselected, user.getEmail());
                                bookingReference.child(key).setValue(book);
                                Toast.makeText(SingleStadium.this, "Booking added successfully",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("Booking error", error.getMessage());
                        }
                    });

                }
            }
        });


        //created a custom hook to display thank you message
        LayoutInflater inflater=getLayoutInflater();
        View layout =inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.toast_layout));

        final Toast toast= new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);




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
    public static String getuserId(){
        String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        return  userID;
    }

    private void clearControls(){
        selectedDate.setText("Selected Time");
        selectedTime.setText("Selected Date");
        dateselected = "";
        timeselected = "";
    }

}
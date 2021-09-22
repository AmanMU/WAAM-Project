package com.waam.book2play;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStadiums extends AppCompatActivity {

    private TextInputLayout sPhoneLayout, sNameLayout, sAddressLayout, sOTLayout, sCTLayout, sPriceLayout;
    private TextInputEditText sPhone, sName, sAddress, sOT, sCT, sPrice;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button sChooseImageButton;
    private Button sRegisterButton;
    private ImageView sImageView;
    private ProgressBar sUploadProgress;
    private Uri imageURI;
    int t2hour, t2minute, t1hour, t1minute;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private StorageTask registerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stadiums);

        sPhone = findViewById(R.id.stadiumPhoneTextField);
        sName = findViewById(R.id.stadiumNameTextField);
        sAddress = findViewById(R.id.stadiumAddressTextField);
        sOT = findViewById(R.id.stadiumOTTextField);
        sCT = findViewById(R.id.stadiumCTTextField);
        sPrice = findViewById(R.id.stadiumPriceTextField);
        sPhoneLayout = findViewById(R.id.stadiumPhoneLayout);
        sNameLayout = findViewById(R.id.stadiumNameLayout);
        sAddressLayout = findViewById(R.id.stadiumAddressLayout);
        sOTLayout = findViewById(R.id.openTimeLayout);
        sCTLayout = findViewById(R.id.closeTimeLayout);
        sPriceLayout = findViewById(R.id.stadiumPriceLayout);
        sChooseImageButton = findViewById(R.id.btn_addImage);
        sRegisterButton = findViewById(R.id.btn_stadiumRegister);
        sImageView = findViewById(R.id.iv_sUploadImage);
        sUploadProgress = findViewById(R.id.progressBar);
        storageRef = FirebaseStorage.getInstance().getReference("stadiums");
        databaseRef = FirebaseDatabase.getInstance().getReference("stadiums");

        sChooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        sOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOT();
            }
        });

        sCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCT();
            }
        });

        sRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerTask != null && registerTask.isInProgress()){
                    Toast.makeText(AddStadiums.this, "Action in progress, please wait.", Toast.LENGTH_SHORT).show();
                }else {
                    registerStadium();
                }
            }
        });
    }

    private boolean validatePhone(){
        String phone = sPhone.getText().toString().trim();
        if (phone.isEmpty()){
            sPhoneLayout.setError("Field Cannot be empty.");
            return false;
        }else if (!(phone.length()==10)){
            sPhoneLayout.setError("Phone number should be 10 digits.");
            return false;
        }else{
            sPhoneLayout.setError(null);
            return true;
        }
    }

    private boolean validateStadiumName(){
        String sname = sName.getText().toString().trim();
        if (sname.isEmpty()){
            sNameLayout.setError("Field Cannot be empty.");
            return false;
        }else{
            sNameLayout.setError(null);
            return true;
        }
    }

    private boolean validateLocation(){
        String location = sAddress.getText().toString().trim();
        if (location.isEmpty()){
            sAddressLayout.setError("Field Cannot be empty.");
            return false;
        }else {
            sAddressLayout.setError(null);
            return true;
        }
    }

    private boolean validateOpenTime(){
        String ot = sOT.getText().toString().trim();
        if (ot.isEmpty()){
            sOTLayout.setError("Field Cannot be empty.");
            return false;
        }else {
            sOTLayout.setError(null);
            return true;
        }
    }

    private boolean validateCloseTime(){
        String ct = sCT.getText().toString().trim();
        if (ct.isEmpty()){
            sCTLayout.setError("Field Cannot be empty.");
            return false;
        }else {
            sCTLayout.setError(null);
            return true;
        }
    }

    private boolean validatePrice(){
        String price = sPrice.getText().toString().trim();
        if (price.isEmpty()){
            sPriceLayout.setError("Field Cannot be empty.");
            return false;
        }else {
            sPriceLayout.setError(null);
            return true;
        }
    }

    public void registerStadium(){
        if(!validatePhone() | !validateStadiumName() | !validateLocation() | !validateOpenTime() | !validateCloseTime() | !validatePrice()) {
            return;
        }else if (imageURI != null){
            String email = "asel@gmail.com";
            String phone = sPhone.getText().toString().trim();
            String sname = sName.getText().toString().trim();
            String location = sAddress.getText().toString().trim();
            String ot = sOT.getText().toString().trim();
            String ct = sCT.getText().toString().trim();
            String price = sPrice.getText().toString().trim();
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageURI));

            registerTask = fileReference.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            sUploadProgress.setProgress(0);
                                        }
                                    }, 1000);
                                    Toast.makeText(AddStadiums.this, "Stadium successfully registered.", Toast.LENGTH_SHORT).show();
                                    StadiumRegister stadiumRegister = new StadiumRegister(email, phone, sname, location, ot, ct, price,
                                            uri.toString());
                                    String uploadID = databaseRef.push().getKey();
                                    databaseRef.child(uploadID).setValue(stadiumRegister);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStadiums.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            sUploadProgress.setProgress((int) progress);
                        }
                    });

        }else {
            Toast.makeText(this, "Please add an image of your stadium.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageURI = data.getData();
            sImageView.setImageURI(imageURI);
        }
    }

    private void setCT(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                AddStadiums.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        t2hour = i;
                        t2minute = i1;

                        String time = t2hour + ":" + t2minute;

                        SimpleDateFormat f24hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            sCT.setText(f12hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t2hour, t2minute);
        timePickerDialog.show();
    }

    private void setOT(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                AddStadiums.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        t1hour = i;
                        t1minute = i1;

                        String time = t1hour + ":" + t1minute;

                        SimpleDateFormat f24hours = new SimpleDateFormat(
                                "HH:mm"
                        );
                        try {
                            Date date = f24hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat(
                                    "hh:mm aa"
                            );
                            sOT.setText(f12hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 12, 0, false
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1hour, t1minute);
        timePickerDialog.show();
    }
}
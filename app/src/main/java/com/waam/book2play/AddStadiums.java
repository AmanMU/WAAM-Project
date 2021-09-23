package com.waam.book2play;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStadiums extends Fragment {

    private TextInputLayout sPhoneLayout, sNameLayout, sAddressLayout, sOTLayout, sCTLayout, sPriceLayout, sTypeLayout;
    private AutoCompleteTextView sType;
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
    private FirebaseUser user;

    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Stadium");
        View v = inflater.inflate(R.layout.activity_add_stadiums, container, false);

        sPhone = v.findViewById(R.id.stadiumPhoneTextField);
        sName = v.findViewById(R.id.stadiumNameTextField);
        sAddress = v.findViewById(R.id.stadiumAddressTextField);
        sOT = v.findViewById(R.id.stadiumOTTextField);
        sCT = v.findViewById(R.id.stadiumCTTextField);
        sPrice = v.findViewById(R.id.stadiumPriceTextField);
        sPhoneLayout = v.findViewById(R.id.stadiumPhoneLayout);
        sNameLayout = v.findViewById(R.id.stadiumNameLayout);
        sAddressLayout = v.findViewById(R.id.stadiumAddressLayout);
        sOTLayout = v.findViewById(R.id.openTimeLayout);
        sCTLayout = v.findViewById(R.id.closeTimeLayout);
        sPriceLayout = v.findViewById(R.id.stadiumPriceLayout);
        sChooseImageButton = v.findViewById(R.id.btn_addImage);
        sRegisterButton = v.findViewById(R.id.btn_updateStadium);
        sImageView = v.findViewById(R.id.iv_sUploadImage);
        sUploadProgress = v.findViewById(R.id.progressBar);
        storageRef = FirebaseStorage.getInstance().getReference("stadiums");
        databaseRef = FirebaseDatabase.getInstance().getReference("stadiums");
        user = FirebaseAuth.getInstance().getCurrentUser();
        sTypeLayout = v.findViewById(R.id.stadiumTypeLayout);
        sType = v.findViewById(R.id.stadiumAutoComplete);

        String[] items = new String[]{
                "Futsal",
                "Cricket",
                "Badminton"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_item,
                items
        );

        sType.setAdapter(adapter);

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
                if (registerTask != null && registerTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Action in progress, please wait.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        registerStadium();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return v;
    }

    //validation of inputs
    private boolean validatePhone() {
        String phone = sPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            sPhoneLayout.setError("Field Cannot be empty.");
            return false;
        } else if (!(phone.length() == 10)) {
            sPhoneLayout.setError("Phone number should be 10 digits.");
            return false;
        } else {
            sPhoneLayout.setError(null);
            return true;
        }
    }

    private boolean validateStadiumName() {
        String sname = sName.getText().toString().trim();
        if (sname.isEmpty()) {
            sNameLayout.setError("Field Cannot be empty.");
            return false;
        } else {
            sNameLayout.setError(null);
            return true;
        }
    }

    private boolean validateLocation() {
        String location = sAddress.getText().toString().trim();
        if (location.isEmpty()) {
            sAddressLayout.setError("Field Cannot be empty.");
            return false;
        } else {
            sAddressLayout.setError(null);
            return true;
        }
    }

    private boolean validateOpenTime() {
        String ot = sOT.getText().toString().trim();
        if (ot.isEmpty()) {
            sOTLayout.setError("Field Cannot be empty.");
            return false;
        } else {
            sOTLayout.setError(null);
            return true;
        }
    }

    private boolean validateCloseTime() {
        String ct = sCT.getText().toString().trim();
        if (ct.isEmpty()) {
            sCTLayout.setError("Field Cannot be empty.");
            return false;
        } else {
            sCTLayout.setError(null);
            return true;
        }
    }

    private boolean validatePrice() {
        String price = sPrice.getText().toString().trim();
        if (price.isEmpty()) {
            sPriceLayout.setError("Field Cannot be empty.");
            return false;
        } else {
            sPriceLayout.setError(null);
            return true;
        }
    }

    private boolean validateType() {
        String type = sType.getText().toString().trim();
        if (type.isEmpty()) {
            sTypeLayout.setError("Field Cannot be empty.");
            return false;
        } else {
            sTypeLayout.setError(null);
            return true;
        }
    }

    //Creating the stadium
    public void registerStadium() throws Exception {
        if (!validatePhone() | !validateStadiumName() | !validateLocation() | !validateOpenTime() | !validateCloseTime() | !validatePrice() | !validateType()) {
            return;
        } else if (imageURI != null) {
            NoOfSessionsCal sessionNumber = new NoOfSessionsCal();
            String email = user.getEmail();
            String phone = sPhone.getText().toString().trim();
            String sname = sName.getText().toString().trim();
            String location = sAddress.getText().toString().trim();
            String ot = sOT.getText().toString().trim();
            String ct = sCT.getText().toString().trim();
            String price = sPrice.getText().toString().trim();
            String type = sType.getText().toString().trim();
            String noSessions = sessionNumber.calcDif(ot, ct);
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
                                    Toast.makeText(getActivity(), "Stadium successfully registered.", Toast.LENGTH_SHORT).show();
                                    StadiumRegister stadiumRegister = new StadiumRegister(email, phone, sname, location, ot, ct, price, type, noSessions,
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
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            sUploadProgress.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(getActivity(), "Please add an image of your stadium.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = requireActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            sImageView.setImageURI(imageURI);
        }
    }

    //time pickers
    public void setCT() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                android.R.style.Theme_Material_Light_Dialog,
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

    public void setOT() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getActivity(),
                android.R.style.Theme_Material_Light_Dialog,
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
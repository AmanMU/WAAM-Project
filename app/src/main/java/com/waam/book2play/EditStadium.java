package com.waam.book2play;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditStadium extends AppCompatActivity {
    private TextInputLayout sPhoneLayout, sNameLayout, sAddressLayout, sOTLayout, sCTLayout, sPriceLayout, sTypeLayout;
    private AutoCompleteTextView sType;
    private TextInputEditText sPhone, sName, sAddress, sOT, sCT, sPrice;
    private Button sEditButton;
    private Toolbar toolbar;
    private DatabaseReference databaseRef;
    private StadiumRegister stadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stadium);
        Intent i = getIntent();
        stadium = (StadiumRegister) i.getSerializableExtra("stadium");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Stadium");
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        sPhone = findViewById(R.id.stadiumPhoneTextField);
        sPhone.setText(stadium.getsPhone());
        sName = findViewById(R.id.stadiumNameTextField);
        sName.setText(stadium.getsName());
        sAddress = findViewById(R.id.stadiumAddressTextField);
        sAddress.setText(stadium.getsLocation());
        sOT = findViewById(R.id.stadiumOTTextField);
        sOT.setText(stadium.getsOT());
        sCT = findViewById(R.id.stadiumCTTextField);
        sCT.setText(stadium.getsCT());
        sPrice = findViewById(R.id.stadiumPriceTextField);
        sPrice.setText(stadium.getsPrice());

        sPhoneLayout = findViewById(R.id.stadiumPhoneLayout);
        sNameLayout = findViewById(R.id.stadiumNameLayout);
        sAddressLayout = findViewById(R.id.stadiumAddressLayout);
        sAddressLayout = findViewById(R.id.stadiumAddressLayout);
        sOTLayout = findViewById(R.id.openTimeLayout);
        sCTLayout = findViewById(R.id.closeTimeLayout);
        sPriceLayout = findViewById(R.id.stadiumPriceLayout);

        sEditButton = findViewById(R.id.btn_updateStadium);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("stadiums");

        sTypeLayout = findViewById(R.id.stadiumTypeLayout);
        sType = findViewById(R.id.stadiumAutoComplete);
        sType.setText(stadium.getsType());

        String[] items = new String[]{
                "Futsal",
                "Cricket",
                "Badminton"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                EditStadium.this,
                R.layout.dropdown_item,
                items
        );

        sType.setAdapter(adapter);

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        sCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker picker = new
                        MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .build();
                picker.show(getSupportFragmentManager(), "close");
                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                        cal.set(Calendar.MINUTE, picker.getMinute());
                        cal.setLenient(false);
                        String format = formatter.format(cal.getTime());
                        sCT.setText(format);
                    }
                });
            }
        });
        sOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker picker = new
                        MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(10)
                        .build();
                picker.show(getSupportFragmentManager(), "open");
                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                        cal.set(Calendar.MINUTE, picker.getMinute());
                        cal.setLenient(false);
                        String format = formatter.format(cal.getTime());
                        sOT.setText(format);
                    }
                });
            }
        });
    }

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

    public void updateStadium(View view) {
        if (!validatePhone() | !validateStadiumName() | !validateLocation() | !validateOpenTime() | !validateCloseTime() | !validatePrice() | !validateType()) {
            return;
        } else {
            String phone = sPhone.getText().toString().trim();
            String sname = sName.getText().toString().trim();
            String location = sAddress.getText().toString().trim();
            String ot = sOT.getText().toString().trim();
            String ct = sCT.getText().toString().trim();
            String price = sPrice.getText().toString().trim();
            String type = sType.getText().toString().trim();

            DatabaseReference stadiumRef = databaseRef.child(stadium.getsKey());
            Map<String, Object> stadiumUpdates = new HashMap<>();
            stadiumUpdates.put("sPhone", phone);
            stadiumUpdates.put("sName", sname);
            stadiumUpdates.put("sLocation", location);
            stadiumUpdates.put("sOT", ot);
            stadiumUpdates.put("sCT", ct);
            stadiumUpdates.put("sPrice", price);
            stadiumUpdates.put("sType", type);

            stadiumRef.updateChildren(stadiumUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    startActivity(new Intent(EditStadium.this, StadiumOwnerHome.class));
                }
            });
        }

    }
}
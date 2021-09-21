package com.waam.book2play;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URI;

public class AddStadiums extends AppCompatActivity {

    private TextInputLayout sPhoneLayout, sNameLayout, sAddressLayout, sOTLayout, sCTLayout, sPriceLayout;
    private TextInputEditText sPhone, sName, sAddress, sOT, sCT, sPrice;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button sChooseImageButton;
    private Button sRegisterButton;
    private ImageView sImageView;
    private ProgressBar sUploadProgress;
    private Uri imageURI;

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

        sChooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
    }

    private boolean validatePhone(){
        String phone = sPhone.getText().toString().trim();
        if (phone.isEmpty()){
            sPhoneLayout.setError("Field Cannot be empty.");
            return false;
        }else if (!(phone.length()==10)){
            sPhoneLayout.setError("Phone number format is wrong.");
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
            sName.setError(null);
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


    public void confirmInput(View view){
        if(!validatePhone() | !validateStadiumName() | !validateLocation() | !validateOpenTime() | !validateCloseTime() | !validatePrice()) {
            return;
        }
        String input = sPhone.getText().toString().trim();
        input += "\n";
        input += sName.getText().toString().trim();
        input += "\n";
        input += sAddress.getText().toString().trim();
        input += "\n";
        input += sOT.getText().toString().trim();
        input += "\n";
        input += sCT.getText().toString().trim();
        input += "\n";
        input += sPrice.getText().toString().trim();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
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
}
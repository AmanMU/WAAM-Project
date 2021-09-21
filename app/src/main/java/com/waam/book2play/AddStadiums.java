package com.waam.book2play;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddStadiums extends AppCompatActivity {

    private TextInputLayout sPhoneLayout, sNameLayout, sAddressLayout, sOTLayout, sCTLayout, sPriceLayout;
    private TextInputEditText sPhone, sName, sAddress, sOT, sCT, sPrice;

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
            sName.setError("Field Cannot be empty.");
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

}
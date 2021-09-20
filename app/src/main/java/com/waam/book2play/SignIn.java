package com.waam.book2play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void onLoginBtnClicked(View view) {

    }
    public void onRegisterBtnClicked(View view){
        startActivity(new Intent(SignIn.this, SelectUserType.class));
    }
}
package com.waam.book2play;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText edtEmail, edtPassword;
    private TextInputLayout edtEmailLayout, edtPwdLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar signupToolbar =
                (Toolbar) findViewById(R.id.signup_toolbar);
        setSupportActionBar(signupToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.emailTextField);
        edtPassword = findViewById(R.id.passwordTextField);
        edtEmailLayout = findViewById(R.id.signupEmailLayout);
        edtPwdLayout = findViewById(R.id.signupPwdLayout);
    }

    public void registerBtnClicked(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        edtEmailLayout.setErrorEnabled(false);
        edtPwdLayout.setErrorEnabled(false);

        if (TextUtils.isEmpty(email)) {
            edtEmailLayout.setError("Email is required");
            edtEmailLayout.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailLayout.setError("Please provide a valid email");
            edtEmailLayout.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password))  {
            edtPwdLayout.setError("Password is required");
            edtPwdLayout.requestFocus();
            return;
        }

    }

    public void onLoginBtnClicked(View view){
        startActivity(new Intent(SignUp.this, SignIn.class));
    }

}
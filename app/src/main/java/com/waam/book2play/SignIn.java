package com.waam.book2play;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText edtEmail, edtPassword;
    private TextInputLayout edtEmailLayout, edtPwdLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.signinEmailTextField);
        edtPassword = findViewById(R.id.signinPasswordTextField);
        edtEmailLayout = findViewById(R.id.signinEmailLayout);
        edtPwdLayout = findViewById(R.id.signinPwdLayout);
        progressBar = findViewById(R.id.progressBar);
    }

    public void onLoginBtnClicked(View view) {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        edtEmailLayout.setErrorEnabled(false);
        edtPwdLayout.setErrorEnabled(false);

        if (TextUtils.isEmpty(email)) {
            edtEmailLayout.setError("Email is required");
            edtEmailLayout.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailLayout.setError("Please provide a valid email");
            edtEmailLayout.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtPwdLayout.setError("Password is required");
            edtPwdLayout.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtPwdLayout.setError("Minimum password length is 6 characters");
            edtPwdLayout.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        startActivity(new Intent(SignIn.this, MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(SignIn.this,"Login failed! Please check email and password", Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> SignIn.super.onBackPressed()).create().show();
    }

    public void onRegisterBtnClicked(View view) {
        startActivity(new Intent(SignIn.this, SelectUserType.class));
    }
}
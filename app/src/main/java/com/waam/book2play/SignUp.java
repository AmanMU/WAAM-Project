package com.waam.book2play;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText edtName,edtEmail, edtPassword;
    private TextInputLayout edtNameLayout,edtEmailLayout, edtPwdLayout;
    private ProgressBar progressBar;
    private static final String TYPE_PLAYER = "Player";
    private static final String TYPE_STADIUM_OWNER = "StadiumOwner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar signupToolbar = findViewById(R.id.signup_toolbar);
        setSupportActionBar(signupToolbar);
        getSupportActionBar().setTitle("Registration");

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        edtName = findViewById(R.id.nameTextField);
        edtEmail = findViewById(R.id.emailTextField);
        edtPassword = findViewById(R.id.passwordTextField);
        edtNameLayout = findViewById(R.id.signupNameLayout);
        edtEmailLayout = findViewById(R.id.signupEmailLayout);
        edtPwdLayout = findViewById(R.id.signupPwdLayout);
        progressBar = findViewById(R.id.progressBar);
    }

    public void registerBtnClicked(View view) {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String type = getIntent().getStringExtra("userType");
        edtEmailLayout.setErrorEnabled(false);
        edtPwdLayout.setErrorEnabled(false);

        if (TextUtils.isEmpty(name)) {
            edtNameLayout.setError("Name is required");
            edtNameLayout.requestFocus();
            return;
        }
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
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(name, email, type);
                        FirebaseDatabase.getInstance("https://book2play-waam-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this, "Registration Successful!",Toast.LENGTH_SHORT).show();
                                        if (type.equals(TYPE_PLAYER)) {
                                            startActivity(new Intent(SignUp.this, MainActivity.class));
                                        } else {
                                            startActivity(new Intent(SignUp.this, StadiumOwnerHome.class));
                                        }
                                        finish();
                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp.this, "Error! Registration failed",Toast.LENGTH_LONG).show();
                                        Log.d("Database", "Database Fail");
                                    }
                                });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignUp.this, "Error! Please check your email and try again!",Toast.LENGTH_LONG).show();
                    }
                });


    }

    public void onLoginBtnClicked(View view) {
        startActivity(new Intent(SignUp.this, SignIn.class));
    }

}
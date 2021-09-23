package com.waam.book2play;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private TextInputEditText edtEmail, edtPassword;
    private TextInputLayout edtEmailLayout, edtPwdLayout;
    private ProgressBar progressBar;

    private static final String TYPE_PLAYER = "Player";
    private static final String TYPE_STADIUM_OWNER = "StadiumOwner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("Users");

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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = user.getUid();
                        userRef = FirebaseDatabase.getInstance().getReference("Users");
                        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User currentUser = dataSnapshot.getValue(User.class);

                                if (currentUser != null && currentUser.type.equals(TYPE_PLAYER)) {
                                    Log.d("Type", currentUser.type);
                                    startActivity(new Intent(SignIn.this, MainActivity.class));
                                    finish();
                                } else if (currentUser != null && currentUser.type.equals(TYPE_STADIUM_OWNER)) {
                                    Log.d("Type", currentUser.type.toString());
                                    startActivity(new Intent(SignIn.this, StadiumOwnerHome.class));
                                    finish();
                                } else
                                    Toast.makeText(SignIn.this, "Error", Toast.LENGTH_LONG).show();

                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(SignIn.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SignIn.this, "Login failed! Please check email and password", Toast.LENGTH_LONG).show();
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

    public void registerBtnClicked(View view) {
        startActivity(new Intent(SignIn.this, SelectUserType.class));
    }
}
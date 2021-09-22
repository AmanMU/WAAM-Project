package com.waam.book2play;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TYPE_PLAYER = "Player";
    private static final String TYPE_STADIUM_OWNER = "StadiumOwner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user == null) {
                startActivity(new Intent(LoadingActivity.this, SignIn.class));
                finish();
            }
            else {
                String userID = user.getUid();
                userRef = FirebaseDatabase.getInstance().getReference("Users");
                userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User currentUser = snapshot.getValue(User.class);

                        if(currentUser != null && currentUser.type.equals(TYPE_PLAYER)) {
                            Log.d("Type", currentUser.type);
                            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                            finish();
                        }
                        else if(currentUser != null && currentUser.type.equals(TYPE_STADIUM_OWNER)) {
                            Log.d("Type", currentUser.type.toString());
                            startActivity(new Intent(LoadingActivity.this, StadiumOwnerHome.class));
                            finish();
                        }
                        else
                            Toast.makeText(LoadingActivity.this, currentUser.type.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoadingActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
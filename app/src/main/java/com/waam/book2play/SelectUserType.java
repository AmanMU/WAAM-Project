package com.waam.book2play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectUserType extends AppCompatActivity {

    private static final String TYPE_PLAYER = "Player";
    private static final String TYPE_STADIUM_OWNER = "StadiumOwner";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);
    }

    public void onUserTypeSelected(View view){
        Intent intent = new Intent(SelectUserType.this, SignUp.class);
        if(view.getId() == R.id.playerBtn)
            intent.putExtra("userType", TYPE_PLAYER);
        else
            intent.putExtra("userType", TYPE_STADIUM_OWNER);

        startActivity(intent);
    }

    public void onLoginBtnClicked(View view){
        startActivity(new Intent(SelectUserType.this, SignIn.class));
    }
}
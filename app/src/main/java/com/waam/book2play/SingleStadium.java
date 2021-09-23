package com.waam.book2play;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class SingleStadium extends AppCompatActivity {
    private ImageView sImage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_stadium);
        Intent i = getIntent();
        StadiumRegister stadium = (StadiumRegister) i.getSerializableExtra("stadium");
        toolbar = findViewById(R.id.toolbar);
        sImage = findViewById(R.id.stadium_image);
        toolbar.setTitle(stadium.getsName());
        Picasso.get().load(stadium.getsImageURL())
                .fit()
                .centerCrop()
                .into(sImage);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
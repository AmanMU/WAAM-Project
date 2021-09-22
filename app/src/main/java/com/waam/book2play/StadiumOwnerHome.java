package com.waam.book2play;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class StadiumOwnerHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_owner_home);


        toolbar = findViewById(R.id.owner_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.oDrawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_ownerHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.oFragment_container, new Home()).commit();
                break;
            case R.id.nav_myStadiums:
                getSupportFragmentManager().beginTransaction().replace(R.id.oFragment_container, new MyStadiums()).commit();
                break;
            case R.id.nav_addStadium:
                //getSupportFragmentManager().beginTransaction().replace(R.id.oFragment_container, new MyStadiums()).commit();
                break;
            case R.id.nav_ownerLogout:
                //mAuth.signOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
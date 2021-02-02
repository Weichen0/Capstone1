package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UI_DoctorMenu extends AppCompatActivity {
    ImageButton newpatient, registeridr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);

        newpatient = (ImageButton)findViewById(R.id.btn_newpatient);
        registeridr = (ImageButton)findViewById(R.id.button_register_idr) ;

        //Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_graphs:
                        startActivity(new Intent(getApplicationContext()
                                , UI_GraphMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_factors:
                        startActivity(new Intent(getApplicationContext()
                                , UI_FactorsMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_more:
                        startActivity(new Intent(getApplicationContext()
                                , UI_ActionMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , UI_MainMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        newpatient.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_DoctorMenu.this, Form_Register.class);
                startActivity(launchActivity);
            }
        });

        registeridr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_DoctorMenu.this, Form_RegisterIDR.class);
                startActivity(launchActivity);
            }
        });
    }
}
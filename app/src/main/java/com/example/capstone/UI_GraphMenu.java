package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UI_GraphMenu extends AppCompatActivity {
    ImageButton barchartf, barchartdvu, barcharteva, barchartl, piechartd, piecharti, lineidr;
    private int shortAnimationDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_u_i__graph_menu);
        barchartl= (ImageButton) findViewById(R.id.btn_bar_l);
        barchartdvu = (ImageButton)findViewById(R.id.btn_bar_dvu);
        barcharteva = (ImageButton)findViewById(R.id.btn_bar_eva);
        barchartf = (ImageButton)findViewById(R.id.btn_bar_f);
        piechartd = (ImageButton) findViewById(R.id.btn_pie_d);
        piecharti = (ImageButton) findViewById(R.id.btn_pie_i);
        lineidr = (ImageButton) findViewById(R.id.btn_line_idr);



        barchartl.setFocusable(false);
        barchartdvu.setFocusable(false);
        barcharteva.setFocusable(false);
        barchartf.setFocusable(false);
        piecharti.setFocusable(false);
        piechartd.setFocusable(false);
        lineidr.setFocusable(false);

        //Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.nav_graphs);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , UI_MainMenu.class));
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
                    case R.id.nav_graphs:

                        return true;
                }
                return false;
            }
        });




        barchartf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this,Bar_Factor.class);
                startActivity(launchActivity);
            }
        });

        barchartdvu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this, Bar_DoctorUser.class);
                startActivity(launchActivity);
            }
        });

        barcharteva.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this,Bar_Expect_Actual.class);
                startActivity(launchActivity);
            }
        });


        barchartl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this,Bar_Location.class);
                startActivity(launchActivity);
            }
        });

        piechartd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this,Pie_DoctorResponse.class);
                startActivity(launchActivity);
            }
        });

        piecharti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this, Pie_Infected.class);
                startActivity(launchActivity);
            }
        });

        lineidr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V){
                Intent launchActivity= new Intent(UI_GraphMenu.this,Line_IDR.class);
                startActivity(launchActivity);
            }
        });


    }


}
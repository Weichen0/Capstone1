package com.example.capstone;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UI_FactorsMenu extends AppCompatActivity {
    ListView listView;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i__factors_menu);
        listView = findViewById(R.id.list_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);



        final String Factors [] = {"Primary", "Indirect", "Contact", "Exposure",
                            "Secondary", "Cluster", "Environmental", "Touch", "Hygiene",
                            "Exposure", "Local Travel", "International Travel", "Visit Friends", "Religious Activity", "Sports", "Restaurant/ Hospitality", "Work/ School"};
        Integer FactorImg [] = {R.drawable.primary_btn, R.drawable.primary1, R.drawable.primary2, R.drawable.primary3,
                                R.drawable.secondary_btn, R.drawable.secondary1, R.drawable.secondary2, R.drawable.secondary3, R.drawable.secondary4,
                                R.drawable.exposure_btn, R.drawable.exposure1, R.drawable.exposure2, R.drawable.exposure3, R.drawable.exposure4, R.drawable.exposure5, R.drawable.exposure6, R.drawable.exposure7};

        adapter=new ListAdapter(this, Factors, FactorImg);
        listView.setTextFilterEnabled(true);
        listView.setAdapter(adapter);




        //Nav Bar
        bottomNavigationView.setSelectedItemId(R.id.nav_factors);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_graphs:
                        startActivity(new Intent(getApplicationContext()
                                , UI_GraphMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , UI_MainMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_more:
                        startActivity(new Intent(getApplicationContext()
                                , UI_ActionMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_factors:

                        return true;
                }
                return false;
            }
        });
    }


}
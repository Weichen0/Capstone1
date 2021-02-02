package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class UI_MainMenu extends AppCompatActivity {
    ImageButton questionnaire, dmenu, gmenu, fmenu, news;

    TextView itotal, dtotal, rtotal, today;
    FirebaseDatabase database;
    DatabaseReference ref1, ref2;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    int i, d, r;
    String date;

    int [] idr = {0,0,0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        questionnaire = (ImageButton) findViewById(R.id.btn_questionnaire);
        itotal = (TextView) findViewById(R.id.txt_menu_idr_i);
        dtotal = (TextView) findViewById(R.id.txt_menu_idr_d);
        rtotal = (TextView) findViewById(R.id.txt_menu_idr_r);
        today = (TextView) findViewById(R.id.txt_menu_idr_date);


        dmenu = (ImageButton) findViewById(R.id.btn_dmenu);
        gmenu = (ImageButton) findViewById(R.id.btn_gmenu);
        fmenu = (ImageButton) findViewById(R.id.btn_factor);
        news = (ImageButton) findViewById(R.id.btn_news);


        database = FirebaseDatabase.getInstance();
        ref1 = database.getReference("user");
        ref2 = database.getReference("idr");

        //Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

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

                        return true;
                }
                return false;
                }
        });






        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.malaysiakini.com/en/tag/covid-19")));
            }
        });


        questionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                Query query = ref1.orderByKey().equalTo(user.getUid());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            int check = userSnapshot.child("qCount").getValue(Integer.class);

                            if (check == 1) {
                                Intent launchActivity = new Intent(UI_MainMenu.this, Form_Questionnaire.class);
                                startActivity(launchActivity);
                            } else {
                                Toast.makeText(UI_MainMenu.this, "You have already submitted a response", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



        gmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent launchActivity = new Intent(UI_MainMenu.this, UI_GraphMenu.class);
                startActivity(launchActivity);
            }
        });
        fmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent launchActivity = new Intent(UI_MainMenu.this, UI_FactorsMenu.class);
                startActivity(launchActivity);
            }
        });


        dmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

                Query query = ref1.orderByKey().equalTo(user.getUid());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String check = userSnapshot.child("userType").getValue(String.class);

                            if (check.equals("Doctor")) {
                                Intent launchActivity = new Intent(UI_MainMenu.this, UI_DoctorMenu.class);
                                startActivity(launchActivity);
                            } else {
                                Toast.makeText(UI_MainMenu.this, "This Feature is Locked", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        itotal.setText(" ");
        dtotal.setText(" ");
        rtotal.setText(" ");
        today.setText(" ");
        i = 0;
        d = 0;
        r = 0;
        date = " ";
        Query query = ref2.orderByChild("date").limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 0;
                d = 0;
                r = 0;
                String x= " ";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds != null ) {
                        i = ds.child("infected").getValue(Integer.class);
                        d = ds.child("diseased").getValue(Integer.class);
                        r = ds.child("recovered").getValue(Integer.class);
                        x = ds.child("date").getValue().toString();
                    }
                }

                if (idr != null ){
                    itotal.setText(String.valueOf(i));
                    dtotal.setText(String.valueOf(d));
                    rtotal.setText(String.valueOf(r));
                    today.setText("Date: " + x);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}


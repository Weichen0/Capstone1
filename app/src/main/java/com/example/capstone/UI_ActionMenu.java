package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class UI_ActionMenu extends AppCompatActivity {
    Button logout, abtus, abtcovid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i__action_menu);

        logout = (Button) findViewById(R.id.btn_logout);
        abtus = (Button) findViewById(R.id.btn_aboutus);
        abtcovid = (Button) findViewById(R.id.btn_aboutcovid);

        //Nav Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        bottomNavigationView.setSelectedItemId(R.id.nav_more);

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
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , UI_MainMenu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_more:

                        return true;
                }
                return false;
            }
        });
        abtus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                pdf.setpdf("aboutus");
                Intent launchActivity = new Intent(UI_ActionMenu.this, ViewPDF.class);
                startActivity(launchActivity);

            }
        });
        abtcovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                pdf.setpdf("aboutcovid");
                Intent launchActivity = new Intent(UI_ActionMenu.this, ViewPDF.class);
                startActivity(launchActivity);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent launchActivity = new Intent(UI_ActionMenu.this, SignIn.class);
                startActivity(launchActivity);
            }
        });
    }

    static class pdf {
        private static String pdfname;


        public static void setpdf(String file)
        {
            if (file == "aboutus"){
                pdfname = "aboutus.pdf";
            }else{
                pdfname = "aboutcovid.pdf";
            }
        }

        public static String getPDFname(){
            return pdfname;
        }
    }
}
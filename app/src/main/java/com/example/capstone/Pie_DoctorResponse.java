package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.round;

public class Pie_DoctorResponse extends AppCompatActivity {
    PieChart piechartd;
    Button generated;
    Spinner dropd;
    String Fac;
    ArrayList <String> xVal = new ArrayList();
    ArrayList <Integer> yVal = new ArrayList();
    TextView pied1 , pied2, description;
    int total;

    FirebaseDatabase database;
    DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie__doctor_response);
        piechartd = (PieChart) findViewById(R.id.piechart_d);
        generated = (Button) findViewById(R.id.button_generate_pied);
        dropd = (Spinner) findViewById(R.id.spinner_pie_f);
        pied1 = (TextView) findViewById(R.id.txt_pied_1);
        pied2 = (TextView) findViewById(R.id.txt_pied_2);
        description = (TextView) findViewById(R.id.txt_pied_description);
        description.setText("A visualization in the form of a pie chart to visualize the size of factors from doctor’s response. Shows which factor the doctors agree as the most contributed factor.");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("drresponse");

        String[] factors = new String[]{"All", "Primary", "Secondary", "Exposure"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, factors);
        dropd.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        generated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                yVal.clear();
                xVal.clear();
                pied1.setText(" ");
                pied2.setText(" ");
                total =0;
                Fac= dropd.getSelectedItem().toString();

                if (Fac.equals("All")) {
                    xVal.add("indirect");
                    xVal.add("contact");
                    xVal.add("aerosol");
                    xVal.add("clustering");
                    xVal.add("environmental");
                    xVal.add("touch");
                    xVal.add("hygiene");
                    xVal.add("local");
                    xVal.add("international");
                    xVal.add("visit");
                    xVal.add("religion");
                    xVal.add("sports");
                    xVal.add("restaurant");
                    xVal.add("work");

                } else if (Fac.equals("Primary")) {
                    xVal.add("indirect");
                    xVal.add("contact");
                    xVal.add("aerosol");


                } else if (Fac.equals("Secondary")) {
                    xVal.add("clustering");
                    xVal.add("environmental");
                    xVal.add("touch");
                    xVal.add("hygiene");


                } else if (Fac.equals("Exposure")) {
                    xVal.add("local");
                    xVal.add("international");
                    xVal.add("visit");
                    xVal.add("religion");
                    xVal.add("sports");
                    xVal.add("restaurant");
                    xVal.add("work");


                } else {
                    Toast.makeText(Pie_DoctorResponse.this, "Please select a factor", Toast.LENGTH_LONG).show();
                    return;
                }

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (int i = 0; i < xVal.size(); i++) {
                            int c1 = 0;
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                String check = ds1.child(xVal.get(i)).getValue().toString();
                                if (check.equals("1")) {
                                    c1 = c1 + 1;
                                }

                            }
                            yVal.add(c1);
                        }
                        ArrayList dataVal = new ArrayList();
                        for (int i = 0 ; i < yVal.size()  ; i++){
                            if (yVal.get(i)!= 0) {
                                dataVal.add(new PieEntry(yVal.get(i), xVal.get(i)));
                            }
                        }
                        PieDataSet pieDataSet = new PieDataSet(dataVal, " ");
                        pieDataSet.setValueTextColor(Color.rgb(255, 255, 255));
                        ArrayList dataSets= new ArrayList<>();
                        dataSets.add(pieDataSet);
                        PieData data = new PieData(pieDataSet);
                        piechartd.setData(data);
                        pieDataSet.setColors(Format.getColorClassArray());
                        piechartd.setEntryLabelColor(Color.WHITE);
                        Legend legend = piechartd.getLegend();
                        legend.setEnabled(false);
                        piechartd.invalidate();

                        DecimalFormat d  = new DecimalFormat("#.00");

                        if (yVal.size() !=0 && yVal.size() == xVal.size()){
                            String x= "";
                            String y= "";
                            for (int i = 0; i < yVal.size(); i++){
                                total = total + yVal.get(i);
                            }
                            for (int i = 0; i < xVal.size(); i++){
                                x = x + xVal.get(i) +  "\n";
                                float percentage = (float)yVal.get(i) / total *100 ;
                                y = y + Float.valueOf(d.format(percentage)) + "% \n";
                            }
                            pied1.setText(x);
                            pied2.setText(y);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
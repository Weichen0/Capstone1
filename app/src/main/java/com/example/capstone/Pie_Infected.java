package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Pie_Infected extends AppCompatActivity {
    PieChart piechartf;
    Button generatef;
    Spinner dropf;
    String Fac;
    TextView piei1, piei2, description;
    int total;
    ArrayList<String> xVal = new ArrayList();
    ArrayList <Integer> yVal = new ArrayList();

    FirebaseDatabase database;
    DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie__infected);

        piechartf = (PieChart) findViewById(R.id.piechart_f);
        generatef = (Button) findViewById(R.id.btn_generate_pief);
        dropf = (Spinner) findViewById(R.id.spinner_pie_f);
        piei1 = (TextView) findViewById(R.id.txt_piei_1);
        piei2 = (TextView) findViewById(R.id.txt_piei_2);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("infected");

        description = (TextView) findViewById(R.id.txt_piei_description);
        description.setText("A visualization in the form of a pie chart to visualize the size of the factors from the registered patients. Shows the size of each factor from the sample size.");

        String[] factors = new String[]{"All", "Primary", "Secondary", "Exposure"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, factors);
        dropf.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        generatef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                yVal.clear();
                xVal.clear();
                total = 0;
                piei1.setText(" ");
                piei2.setText(" ");
                Fac = dropf.getSelectedItem().toString();

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
                    Toast.makeText(Pie_Infected.this, "Please select a factor", Toast.LENGTH_LONG).show();
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
                        for (int i = 0; i < yVal.size(); i++) {
                            if (yVal.get(i) != 0) {
                                dataVal.add(new PieEntry(yVal.get(i), xVal.get(i)));
                            }
                        }
                        PieDataSet pieDataSet = new PieDataSet(dataVal, " ");
                        pieDataSet.setValueTextColor(Color.rgb(255, 255, 255));
                        ArrayList dataSets = new ArrayList<>();
                        dataSets.add(pieDataSet);
                        PieData data = new PieData(pieDataSet);
                        piechartf.setData(data);
                        pieDataSet.setColors(Format.getColorClassArray());
                        piechartf.setEntryLabelColor(Color.WHITE);
                        Legend legend = piechartf.getLegend();
                        legend.setEnabled(false);
                        piechartf.invalidate();

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
                            piei1.setText(x);
                            piei2.setText(y);
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
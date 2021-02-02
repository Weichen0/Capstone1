package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bar_Factor extends AppCompatActivity {
    BarChart barchart;
    Button bg;
    String label, Fac;
    Spinner factor;
    TextView description, mostf, mostf1, mostf2, mostf3;

    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<Integer> yVal = new ArrayList<Integer>();
    ArrayList<String> xVal = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar__factor);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("infected");
        bg = (Button) findViewById(R.id.button_g);
        factor = (Spinner)findViewById(R.id.spinner_factor);
        description = (TextView) findViewById(R.id.txt_barf_description);
        mostf = (TextView) findViewById(R.id.txt_f_f);
        mostf1 = (TextView) findViewById(R.id.txt_f_f1);
        mostf2 = (TextView) findViewById(R.id.txt_f_f2);
        mostf3 = (TextView) findViewById(R.id.txt_f_f3);


        description.setText(
                "Factors leading to the spread of COVID-19 in Bar Chart. Enabling a better image and comparison between each factor. "
        );


        barchart = (BarChart) findViewById(R.id.barchart_f);
        barchart.setPinchZoom(true);


        String[] factors = new String[]{"All", "Primary", "Secondary", "Exposure"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, factors);
        factor.setAdapter(adapter);


    }
    @Override
    public void onStart() {
        super.onStart();
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                xVal.clear();
                yVal.clear();

                mostf.setText(" ");
                mostf1.setText(" ");
                mostf2.setText(" ");
                mostf3.setText(" ");


                Fac= factor.getSelectedItem().toString();

                if (!TextUtils.isEmpty(Fac)){
                    xVal = Format.getFactors.getxVal(Fac);
                    label = Format.getFactors.getxLabel(Fac);
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
                                } }
                            yVal.add(c1);
                        }

                        ArrayList dataVal = new ArrayList();
                        int j = 1;
                        for (int i = 0 ; i < yVal.size() ; i++){
                            dataVal.add(new BarEntry(j, yVal.get(i)));
                            j++;
                        }

                        // Table showing the text of highest contributing factor
                        if (Fac == "All"){
                            mostf.setText(Format.mostTable.getMostIndex(yVal, xVal).toString());
                            mostf1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal.subList(0,3)), new ArrayList<String>(xVal.subList(0,3))).toString());
                            mostf2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal.subList(3,7)), new ArrayList<String>(xVal.subList(3,7))).toString());
                            mostf3.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal.subList(7,14)), new ArrayList<String>(xVal.subList(7,14))).toString());
                        }else if (Fac == "Primary"){
                            mostf1.setText(Format.mostTable.getMostIndex(yVal, xVal).toString());
                        }else if (Fac == "Secondary"){
                            mostf2.setText(Format.mostTable.getMostIndex(yVal, xVal).toString());
                        }else if (Fac == "Exposure"){
                            mostf3.setText(Format.mostTable.getMostIndex(yVal, xVal).toString());
                        }

                        BarDataSet barDataSet1 = new BarDataSet(dataVal, label);
                        barDataSet1.setColors(new int[]{Color.rgb(19, 141, 117)});

                        ArrayList dataSets= new ArrayList<>();
                        dataSets.add(barDataSet1);
                        BarData data = new BarData(dataSets);
                        barchart.setData(data);
                        barchart.animateXY(3000, 3000);



                        //X axis
                        XAxis xAxis = barchart.getXAxis();
                        xAxis.setAxisMinValue(0);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new Bar_DoctorUser.MyAxisValueFormatter(xVal));
                        xAxis.setLabelRotationAngle(60f);
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setTextSize(12f);

                        //Y axis
                        YAxis yAxis = barchart.getAxisLeft();
                        yAxis.setAxisMinValue(0);
                        yAxis.setAxisMaxValue(barDataSet1.getYMax() +5);

                        //Right Axis
                        YAxis rAxis = barchart.getAxisRight();
                        rAxis.setEnabled(false);


                        float barwidth = 0.3f;


                        //white texts
                        Legend legend = barchart.getLegend();
                        legend.setTextColor(Color.rgb(255, 255, 255));
                        barDataSet1.setValueTextColor(Color.rgb(255, 255, 255));
                        xAxis.setTextColor(Color.rgb(255, 255, 255));
                        yAxis.setTextColor(Color.rgb(255, 255, 255));

                        //legend
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);


                        data.setBarWidth(barwidth);
                        barchart.setTouchEnabled(true);
                        barchart.setDragEnabled(true);
                        barchart.resetViewPortOffsets();
                        xAxis.setAxisMaxValue( xVal.size() +2);
                        barchart.invalidate();



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }
}




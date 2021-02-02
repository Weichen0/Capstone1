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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Bar_Expect_Actual extends AppCompatActivity {
    BarChart barchartea;
    TextView mostd, mostd1, mostd2, mostd3, mosti, mosti1,mosti2, mosti3, description;
    Spinner dropea;
    Button generateea;
    String Fac;

    FirebaseDatabase database;
    DatabaseReference refI, refD;

    ArrayList<Integer> yValD = new ArrayList<Integer>();
    ArrayList<Integer> yValI = new ArrayList<Integer>();
    ArrayList<String> xVal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar__expect__actual);

        description = (TextView) findViewById(R.id.txt_ea_description);
        description.setText("A visualization in the form of a bar chart to compare the doctor’s opinion against the recorded infection factors. The frequency of the suggested factor will be compared instead of the sample size. This will allow us to realize which factors needs more attention than expected.");
        barchartea = (BarChart) findViewById(R.id.barchart_f);
        dropea = (Spinner) findViewById(R.id.spinner_ea);
        generateea = (Button) findViewById(R.id.button_generateea);
        database = FirebaseDatabase.getInstance();
        refI = database.getReference("infected");
        refD = database.getReference("drresponse");
        mostd = (TextView) findViewById(R.id.txt_ea_mostd);
        mostd1 = (TextView) findViewById(R.id.txt_ea_mostd1);
        mostd2 = (TextView) findViewById(R.id.txt_ea_mostd2);
        mostd3 = (TextView) findViewById(R.id.txt_ea_mostd3);
        mosti = (TextView) findViewById(R.id.txt_ea_mosti);
        mosti1 = (TextView) findViewById(R.id.txt_ea_mosti1);
        mosti2 = (TextView) findViewById(R.id.txt_ea_mosti2);
        mosti3 = (TextView) findViewById(R.id.txt_ea_mosti3);

        barchartea.setPinchZoom(true);


        String[] factors = new String[]{"All", "Primary", "Secondary", "Exposure"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, factors);
        dropea.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        generateea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                yValD.clear();
                yValI.clear();
                xVal.clear();
                Fac = dropea.getSelectedItem().toString();

                mostd.setText(" ");
                mostd1.setText(" ");
                mostd2.setText(" ");
                mostd3.setText(" ");
                mosti.setText(" ");
                mosti1.setText(" ");
                mosti2.setText(" ");
                mosti3.setText(" ");

                if (!TextUtils.isEmpty(Fac)){
                    xVal = Format.getFactors.getxVal(Fac);
                }

                refD.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int tcount1 = 0;
                        for (int i = 0; i < xVal.size(); i++) {
                            int dcount = 0;
                            tcount1 = 0;
                            for (DataSnapshot factorSnapshot : dataSnapshot.getChildren()) {
                                String dcheck = factorSnapshot.child(xVal.get(i)).getValue().toString();
                                if (dcheck.equals("1")) {
                                    dcount = dcount + 1;
                                }
                                tcount1 += 1;
                            }

                            float cal = (float) dcount / tcount1 * 100;
                            yValD.add((int) cal);


                        }
                        refI.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int tcount2;
                                for (int i = 0; i < xVal.size(); i++) {
                                    int fcount = 0;
                                    tcount2 = 0;
                                    for (DataSnapshot factorSnapshot : dataSnapshot.getChildren()) {
                                        String fcheck = factorSnapshot.child(xVal.get(i)).getValue().toString();
                                        if (fcheck.equals("1")) {
                                            fcount = fcount + 1;
                                        }
                                        tcount2 += 1;
                                    }
                                    float cal = (float) fcount / tcount2 * 100;
                                    yValI.add((int) cal);
                                }
                                ArrayList dataVal1 = new ArrayList();
                                ArrayList dataVal2 = new ArrayList();
                                int x1 = 1;
                                int x2 = 2;
                                for (int i = 0; i < yValD.size(); i++) {
                                    dataVal1.add(new BarEntry(x1, yValD.get(i)));
                                    dataVal2.add(new BarEntry(x2, yValI.get(i)));
                                    x1 += 2;
                                    x2 += 2;
                                }

                                // Table showing the text of highest contributing factor
                                if (Fac == "All") {
                                    mostd.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mosti.setText(Format.mostTable.getMostIndex(yValI, xVal).toString());
                                    mostd1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValD.subList(0, 3)), new ArrayList<String>(xVal.subList(0, 3))).toString());
                                    mosti1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValI.subList(0, 3)), new ArrayList<String>(xVal.subList(0, 3))).toString());
                                    mostd2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValD.subList(3, 7)), new ArrayList<String>(xVal.subList(3, 7))).toString());
                                    mosti2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValI.subList(3, 7)), new ArrayList<String>(xVal.subList(3, 7))).toString());
                                    mostd3.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValD.subList(7, 14)), new ArrayList<String>(xVal.subList(7, 14))).toString());
                                    mosti3.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValI.subList(7, 14)), new ArrayList<String>(xVal.subList(7, 14))).toString());
                                } else if (Fac == "Primary") {
                                    mostd1.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mosti1.setText(Format.mostTable.getMostIndex(yValI, xVal).toString());
                                } else if (Fac == "Secondary") {
                                    mostd2.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mosti2.setText(Format.mostTable.getMostIndex(yValI, xVal).toString());
                                } else if (Fac == "Exposure") {
                                    mostd3.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mosti3.setText(Format.mostTable.getMostIndex(yValI, xVal).toString());
                                }

                                BarDataSet barDataSet1 = new BarDataSet(dataVal1, "Doctor Frequency");
                                BarDataSet barDataSet2 = new BarDataSet(dataVal2, "Actual Frequency");
                                barDataSet1.setColors(new int[]{Color.rgb(19, 141, 117)});
                                barDataSet2.setColors(new int[]{Color.rgb(46, 204, 113)});



                                ArrayList dataSets = new ArrayList<>();
                                dataSets.add(barDataSet1);
                                dataSets.add(barDataSet2);
                                BarData data = new BarData(dataSets);
                                barchartea.setData(data);
                                barchartea.animateXY(3000, 3000);

                                //max

                                //X axis
                                XAxis xAxis = barchartea.getXAxis();
                                xAxis.setAxisMinValue(0);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setValueFormatter(new Bar_DoctorUser.MyAxisValueFormatter(xVal));
                                xAxis.setLabelRotationAngle(60f);
                                xAxis.setGranularity(1f);
                                xAxis.setGranularityEnabled(true);
                                xAxis.setTextSize(12f);

                                //Y axis
                                YAxis yAxis = barchartea.getAxisLeft();
                                yAxis.setAxisMinValue(0);
                                yAxis.setAxisMaxValue(100);

                                //Right Axis
                                YAxis rAxis = barchartea.getAxisRight();
                                rAxis.setEnabled(false);

                                float groupspace = 0.4f;
                                float barspace = 0f;
                                float barwidth = 0.3f;

                                //white text
                                Legend legend = barchartea.getLegend();
                                legend.setTextColor(Color.rgb(255, 255, 255));
                                barDataSet1.setValueTextColor(Color.rgb(255, 255, 255));
                                barDataSet2.setValueTextColor(Color.rgb(255, 255, 255));
                                xAxis.setTextColor(Color.rgb(255, 255, 255));
                                yAxis.setTextColor(Color.rgb(255, 255, 255));

                                //legend
                                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);


                                data.setBarWidth(barwidth);
                                data.groupBars(0.5f, groupspace, barspace);
                                barchartea.setTouchEnabled(true);
                                barchartea.setDragEnabled(true);
                                xAxis.setAxisMaxValue(barchartea.getBarData().getGroupWidth(groupspace, barspace) * xVal.size());
                                barchartea.resetViewPortOffsets();
                                barchartea.invalidate();
                            }



                                @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
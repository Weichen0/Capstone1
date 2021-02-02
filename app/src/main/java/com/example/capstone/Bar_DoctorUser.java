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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bar_DoctorUser extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference refU, refD;
    Spinner factor;
    Button generatedu;
    String Fac;
    TextView mostd, mostd1, mostd2,mostd3, mostu, mostu1, mostu2, mostu3, description;


    BarChart barchart;
    ArrayList<Integer> yValD = new ArrayList<Integer>();
    ArrayList<Integer> yValU = new ArrayList<Integer>();
    ArrayList<String> xVal = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar__du);
        database = FirebaseDatabase.getInstance();
        refU = database.getReference("response");
        refD = database.getReference("drresponse");
        factor = (Spinner) findViewById(R.id.spinner_location_du);
        generatedu = (Button) findViewById(R.id.button_generatedu);

        description = (TextView)findViewById(R.id.txt_idr_description);
        description.setText("A visualization in the form of bar chart to compare the response of doctors’ opinion against users. This will correct the user’s opinion on certain factors and raise awareness.");
        mostd = (TextView) findViewById(R.id.txt_du_mostd);
        mostd1 = (TextView) findViewById(R.id.txt_du_mostd1);
        mostd2= (TextView) findViewById(R.id.txt_du_mostd2);
        mostd3 = (TextView) findViewById(R.id.txt_du_mostd3);

        mostu = (TextView) findViewById(R.id.txt_du_mostu);
        mostu1 = (TextView) findViewById(R.id.txt_du_mostu1);
        mostu2= (TextView) findViewById(R.id.txt_du_mostu2);
        mostu3 = (TextView) findViewById(R.id.txt_du_mostu3);



        barchart = findViewById(R.id.barchart_du);
        barchart.setPinchZoom(true);


        String[] factors = new String[]{"All", "Primary", "Secondary", "Exposure"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, factors);
        factor.setAdapter(adapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        generatedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                yValD.clear();
                yValU.clear();
                xVal.clear();

                mostd.setText(" ");
                mostd1.setText(" ");
                mostd2.setText(" ");
                mostd3.setText(" ");
                mostu.setText(" ");
                mostu1.setText(" ");
                mostu2.setText(" ");
                mostu3.setText(" ");

                Fac = factor.getSelectedItem().toString();

                //get factors
                if (!TextUtils.isEmpty(Fac)){
                    xVal = Format.getFactors.getxVal(Fac);
                }

                refD.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (int i = 0; i < xVal.size(); i++) {
                            int dcount = 0;
                            int tcount1 = 0;
                            for (DataSnapshot factorSnapshot : dataSnapshot.getChildren()) {
                                String dcheck = factorSnapshot.child(xVal.get(i)).getValue().toString();
                                if (dcheck.equals("1")) {
                                    dcount = dcount + 1;
                                }
                                tcount1 = tcount1 + 1;
                            }
                            float cal = (float) dcount / tcount1 * 100;
                            yValD.add((int) cal);

                        }
                        refU.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (int i = 0; i< xVal.size() ; i++) {
                                    int ucount = 0;
                                    int tcount2 = 0;
                                    for (DataSnapshot factorSnapshot : dataSnapshot.getChildren()) {
                                        String ucheck = factorSnapshot.child(xVal.get(i)).getValue().toString();
                                        if (ucheck.equals("1")) {
                                            ucount = ucount + 1;
                                        }
                                        tcount2 = tcount2 + 1;
                                    }
                                    float cal = (float) ucount / tcount2 * 100;
                                    yValU.add((int) cal);
                                }

                                // Table showing the text of highest contributing factor
                                if (Fac == "All"){
                                    mostd.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mostu.setText(Format.mostTable.getMostIndex(yValU, xVal).toString());
                                    mostd1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValD.subList(0,3)), new ArrayList<String>(xVal.subList(0,3))).toString());
                                    mostu1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValU.subList(0,3)), new ArrayList<String>(xVal.subList(0,3))).toString());
                                    mostd2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValD.subList(3,7)), new ArrayList<String>(xVal.subList(3,7))).toString());
                                    mostu2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValU.subList(3,7)), new ArrayList<String>(xVal.subList(3,7))).toString());
                                    mostd3.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValD.subList(7,14)), new ArrayList<String>(xVal.subList(7,14))).toString());
                                    mostu3.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yValU.subList(7,14)), new ArrayList<String>(xVal.subList(7,14))).toString());
                                }else if (Fac == "Primary"){
                                    mostd1.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mostu1.setText(Format.mostTable.getMostIndex(yValU, xVal).toString());
                                }else if (Fac == "Secondary"){
                                    mostd2.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mostu2.setText(Format.mostTable.getMostIndex(yValU, xVal).toString());
                                }else if (Fac == "Exposure"){
                                    mostd3.setText(Format.mostTable.getMostIndex(yValD, xVal).toString());
                                    mostu3.setText(Format.mostTable.getMostIndex(yValU, xVal).toString());
                                }



                                ArrayList dataVal1 = new ArrayList();
                                ArrayList dataVal2 = new ArrayList();;


                                for (int x = 0; x < yValD.size(); x++) {
                                    dataVal1.add(new BarEntry(x, yValD.get(x)));
                                    dataVal2.add(new BarEntry(x, yValU.get(x)));
                                }



                                BarDataSet barDataSet1 = new BarDataSet(dataVal1, "Doctor");
                                barDataSet1.setColors(new int[]{Color.rgb(19, 141, 117)});
                                BarDataSet barDataSet2 = new BarDataSet(dataVal2, "User");
                                barDataSet2.setColors(new int[]{Color.rgb(46, 204, 113)});


                                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                                dataSets.add(barDataSet1);
                                dataSets.add(barDataSet2);
                                BarData data = new BarData(dataSets);

                                barchart.setData(data);
                                barchart.animateXY(3000, 3000);


                                //X axis
                                XAxis xAxis = barchart.getXAxis();
                                xAxis.setAxisMinValue(0);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setValueFormatter(new MyAxisValueFormatter(xVal));
                                xAxis.setLabelRotationAngle(60f);
                                xAxis.setGranularity(1f);
                                xAxis.setGranularityEnabled(true);
                                xAxis.setTextSize(12f);

                                //Y axis
                                YAxis yAxis = barchart.getAxisLeft();
                                yAxis.setAxisMinValue(0);
                                yAxis.setAxisMaxValue(100);

                                //Right Axis
                                YAxis rAxis = barchart.getAxisRight();
                                rAxis.setEnabled(false);

                                float groupspace = 0.4f;
                                float barspace = 0f;
                                float barwidth = 0.3f;

                                //white
                                Legend legend = barchart.getLegend();
                                legend.setTextColor(Color.rgb(255, 255, 255));
                                barDataSet1.setValueTextColor(Color.rgb(255, 255, 255));
                                barDataSet2.setValueTextColor(Color.rgb(255, 255, 255));
                                xAxis.setTextColor(Color.rgb(255, 255, 255));
                                yAxis.setTextColor(Color.rgb(255, 255, 255));

                                //legend
                                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);

                                data.setBarWidth(barwidth);
                                data.groupBars(0.5f ,groupspace,barspace);

                                barchart.setTouchEnabled(true);
                                barchart.setDragEnabled(true);
                                xAxis.setAxisMaxValue(barchart.getBarData().getGroupWidth(groupspace, barspace) * xVal.size() );
                                barchart.invalidate();



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

    public static class MyAxisValueFormatter implements AxisValueFormatter {
        private ArrayList<String> mValues;


        public MyAxisValueFormatter(ArrayList<String> xVal) {
           this.mValues=xVal;
           mValues.add(0, " ");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (value <= mValues.size()-1) {
                return mValues.get((int) value);
            }else {
                return " ";
            }

        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }
    }
}
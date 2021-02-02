package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Bar_Location extends AppCompatActivity {
    Spinner dropfactor;
    EditText location1, location2;
    Button generate;
    BarChart locationchart;
    String Loc1, Loc2, Fac;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> xVal = new ArrayList<String>();
    ArrayList<Integer> yVal1 = new ArrayList<Integer>();
    ArrayList<Integer> yVal2 = new ArrayList<Integer>();
    TextView  mosta1, mostp1, mosts1, moste1, mosta2, mostp2, mosts2, moste2, description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar__location);

        dropfactor = (Spinner) findViewById(R.id.spinner_location_f);
        location1 = (EditText) findViewById(R.id.in_location1);
        location2 = (EditText) findViewById(R.id.in_location2);
        generate = (Button) findViewById(R.id.btn_generate);
        locationchart = (BarChart) findViewById(R.id.barchart_f);
        mosta1 = findViewById(R.id.txt_a_l1);
        mostp1 = findViewById(R.id.txt_p_l1);
        mosts1 = findViewById(R.id.txt_s_l1);
        moste1 = findViewById(R.id.txt_e_l1);
        mosta2 = findViewById(R.id.txt_a_l2);
        mostp2 = findViewById(R.id.txt_p_l2);
        mosts2 = findViewById(R.id.txt_s_l2);
        moste2 = findViewById(R.id.txt_e_l2);

        description = (TextView) findViewById(R.id.txt_barl_description);
        description.setText("A visualization in the form of a bar chart to compare factors across 2 different cities for comparison. E.g. Subang Jaya, Puchong, Klang etc. ");



        database = FirebaseDatabase.getInstance();
        ref = database.getReference("infected");


        String[] factors = new String[]{"All", "Primary", "Secondary", "Exposure"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, factors);
        dropfactor.setAdapter(adapter);


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                yVal1.clear();
                yVal2.clear();
                xVal.clear();

                mosta1.setText(" ");
                mostp1.setText(" ");
                mosts1.setText(" ");
                moste1.setText(" ");
                mosta2.setText(" ");
                mostp2.setText(" ");
                mosts2.setText(" ");
                moste2.setText(" ");

                Fac = dropfactor.getSelectedItem().toString();
                Loc1 = location1.getText().toString().trim();
                Loc2 = location2.getText().toString().trim();

                if (TextUtils.isEmpty(Loc1)){
                   location1.setError ("Please Enter Desired Location");
                   return;
                }
                if(TextUtils.isEmpty(Loc2)){
                    location2.setError("Please Enter Another Location to Compare");
                    return;
                }

                if (!TextUtils.isEmpty(Fac)){
                    xVal = Format.getFactors.getxVal(Fac);
                }


                Query query1 = ref.orderByChild("location").equalTo(Loc1);
                query1.addValueEventListener(new ValueEventListener() {
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
                            yVal1.add(c1);
                        }
                        Query query2 = ref.orderByChild("location").equalTo(Loc2);
                        query2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (int i = 0; i < xVal.size(); i++) {
                                    int c2 = 0;
                                    for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                        String check2 = ds2.child(xVal.get(i)).getValue().toString();
                                        if (check2.equals("1")) {
                                            c2 = c2 + 1;

                                        }
                                    }
                                    yVal2.add(c2);
                                }
                                if (xVal.size()!= 0 && yVal1.size()!=0 && yVal2.size()!=0) {
                                    if (Fac == "All") {
                                        mosta1.setText(Format.mostTable.getMostIndex(yVal1, xVal).toString());
                                        mosta2.setText(Format.mostTable.getMostIndex(yVal2, xVal).toString());
                                        mostp1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal1.subList(0, 3)), new ArrayList<String>(xVal.subList(0, 3))).toString());
                                        mostp2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal2.subList(0, 3)), new ArrayList<String>(xVal.subList(0, 3))).toString());
                                        mosts1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal1.subList(3, 7)), new ArrayList<String>(xVal.subList(3, 7))).toString());
                                        mosts2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal2.subList(3, 7)), new ArrayList<String>(xVal.subList(3, 7))).toString());
                                        moste1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal1.subList(7, 14)), new ArrayList<String>(xVal.subList(7, 14))).toString());
                                        moste2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(yVal2.subList(7, 14)), new ArrayList<String>(xVal.subList(7, 14))).toString());
                                    } else if (Fac == "Primary") {
                                        mostp1.setText(Format.mostTable.getMostIndex(yVal1, xVal).toString());
                                        mostp2.setText(Format.mostTable.getMostIndex(yVal2, xVal).toString());
                                    } else if (Fac == "Secondary") {
                                        mosts1.setText(Format.mostTable.getMostIndex(yVal1, xVal).toString());
                                        mosts2.setText(Format.mostTable.getMostIndex(yVal2, xVal).toString());
                                    } else if (Fac == "Exposure") {
                                        moste1.setText(Format.mostTable.getMostIndex(yVal1, xVal).toString());
                                        moste2.setText(Format.mostTable.getMostIndex(yVal2, xVal).toString());
                                    }
                                }


                                ArrayList dataVal1 = new ArrayList();
                                ArrayList dataVal2 = new ArrayList();

                                for (int i = 0 ; i < yVal1.size()  ; i++){
                                    dataVal1.add(new BarEntry(i, yVal1.get(i)));
                                    dataVal2.add(new BarEntry(i, yVal2.get(i)));

                                }


                                BarDataSet barDataSet1 = new BarDataSet(dataVal1, "Location 1");
                                BarDataSet barDataSet2 = new BarDataSet(dataVal2, "Location 2");
                                barDataSet1.setColors(new int[]{Color.rgb(19, 141, 117)});
                                barDataSet2.setColors(new int[]{Color.rgb(46, 204, 113)});

                                ArrayList dataSets= new ArrayList<>();
                                dataSets.add(barDataSet1);
                                dataSets.add(barDataSet2);
                                BarData data = new BarData(dataSets);
                                locationchart.setData(data);
                                locationchart.animateXY(3000, 3000);

                                int ymax = 0;
                                if (barDataSet1.getYMax() > barDataSet2.getYMax()){
                                    ymax = (int) barDataSet1.getYMax();
                                }else{
                                    ymax = (int) barDataSet2.getYMax();
                                }

                                //X axis
                                XAxis xAxis = locationchart.getXAxis();
                                xAxis.setAxisMinValue(0);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setValueFormatter(new Bar_DoctorUser.MyAxisValueFormatter(xVal));
                                xAxis.setLabelRotationAngle(60f);
                                xAxis.setGranularity(1f);
                                xAxis.setGranularityEnabled(true);
                                xAxis.setTextSize(12f);

                                //Y axis
                                YAxis yAxis = locationchart.getAxisLeft();
                                yAxis.setAxisMinValue(0);
                                yAxis.setAxisMaxValue(ymax);

                                //Right Axis
                                YAxis rAxis = locationchart.getAxisRight();
                                rAxis.setEnabled(false);

                                float groupspace = 0.4f;
                                float barspace = 0f;
                                float barwidth = 0.3f;

                                //white
                                Legend legend = locationchart.getLegend();
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
                                locationchart.setTouchEnabled(true);
                                locationchart.setDragEnabled(true);
                                xAxis.setAxisMaxValue(locationchart.getBarData().getGroupWidth(groupspace, barspace) * xVal.size() );
                                locationchart.resetViewPortOffsets();
                                locationchart.invalidate();



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
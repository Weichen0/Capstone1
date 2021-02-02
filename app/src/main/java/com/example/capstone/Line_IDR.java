package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Line_IDR extends AppCompatActivity {
    LineChart linechart;
    FirebaseDatabase database;
    DatabaseReference ref, ref2;
    Button refresh, find;
    EditText timeinfected;
    TextView f, f1, f2, f3, description1, description2;

    ArrayList<Integer> iVal = new ArrayList<Integer>();
    ArrayList<Integer> dVal = new ArrayList<Integer>();
    ArrayList<Integer> rVal = new ArrayList<Integer>();
    ArrayList<String> xVal = new ArrayList<String>();
    String [] factor = {"indirect", "contact", "aerosol",
            "clustering", "environmental","touch","hygiene",
            "international","local", "visit", "religion", "sports","restaurant","work"};
    ArrayList <Integer> factorVal = new ArrayList<Integer>();
    ArrayList <String> xfactorVal = new ArrayList<String>();


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line__i_d_r);

        linechart = (LineChart) findViewById(R.id.linechart_idr);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("idr");
        refresh = (Button)findViewById(R.id.btn_idr_refresh);
        ref2 = database.getReference("infected");
        timeinfected = (EditText) findViewById(R.id.in_time_infected);
        find= (Button) findViewById(R.id.btn_find_infected);
        f = (TextView) findViewById(R.id.txt_find);
        f1 = (TextView) findViewById(R.id.txt_find1);
        f2 = (TextView) findViewById(R.id.txt_find2);
        f3 = (TextView) findViewById(R.id.txt_find3);
        description1 = (TextView) findViewById(R.id.txt_idr_description);
        description1.setText("A visualization in the form of a line chart to visualize the infected, diseased and recovered ratio against dates. Shows the changes in IDR across time. ");
        description2 = (TextView) findViewById(R.id.txt_day_description);
        description2.setText("A visualization in the form of a table to show which factor contributes the most of a certain date. Includes different category to further analyze the data.");

    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                iVal.clear();
                dVal.clear();
                rVal.clear();
                xVal.clear();
                Query query = ref.orderByChild("date");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            iVal.add(ds.child("infected").getValue(Integer.class));
                            dVal.add(ds.child("diseased").getValue(Integer.class));
                            rVal.add(ds.child("recovered").getValue(Integer.class));
                            xVal.add(ds.child("date").getValue().toString());
                        }
                        ArrayList dataVal1 = new ArrayList();
                        ArrayList dataVal2 = new ArrayList();
                        ArrayList dataVal3 = new ArrayList();
                        for (int i = 0; i<iVal.size(); i++){
                            dataVal1.add(new Entry(i, iVal.get(i)));
                            dataVal2.add(new Entry(i, dVal.get(i)));
                            dataVal3.add(new Entry(i, rVal.get(i)));
                        }
                        LineDataSet linedataset1 = new LineDataSet(dataVal1, "Infected");
                        linedataset1.setColor(Color.rgb(0, 139, 255));
                        linedataset1.setLineWidth(2);
                        LineDataSet linedataset2 = new LineDataSet(dataVal2, "Diseased");
                        linedataset2.setColor(Color.rgb(255, 94, 72));
                        linedataset2.setLineWidth(2);
                        LineDataSet linedataset3 = new LineDataSet(dataVal3, "Recovered");
                        linedataset3.setColor(Color.rgb(0, 255, 108));
                        linedataset3.setLineWidth(2);

                        XAxis xAxis = linechart.getXAxis();
                        xAxis.setAxisMinValue(0);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setValueFormatter(new Line_IDR.LineAxisValueFormatter(xVal));
                        xAxis.setLabelRotationAngle(60f);
                        xAxis.setGranularity(1f);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setTextSize(12f);


                        YAxis yAxis = linechart.getAxisLeft();
                        YAxis rAxis = linechart.getAxisRight();
                        rAxis.setEnabled(false);
                        Legend legend = linechart.getLegend();
                        legend.setTextColor(Color.rgb(255, 255, 255));

                        linedataset1.setValueTextColor(Color.rgb(255, 255, 255));
                        linedataset2.setValueTextColor(Color.rgb(255, 255, 255));
                        linedataset3.setValueTextColor(Color.rgb(255, 255, 255));
                        xAxis.setTextColor(Color.rgb(255, 255, 255));
                        yAxis.setTextColor(Color.rgb(255, 255, 255));

                        //legend
                        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);


                        ArrayList dataSets = new ArrayList<>();
                        dataSets.add(linedataset1);
                        dataSets.add(linedataset2);
                        dataSets.add(linedataset3);
                        LineData data = new LineData(dataSets);
                        linechart.setData(data);
                        linechart.invalidate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                f.setText(" ");
                f1.setText(" ");
                f2.setText(" ");
                f3.setText(" ");
                factorVal.clear();


                for(int i = 0; i < factor.length; i++){
                    xfactorVal.add(factor[i]);
                }

               String d = timeinfected.getText().toString();
                if (TextUtils.isEmpty(d)){
                    timeinfected.setError("Please key in a date");
                    return;
                }else if (TextUtils.getTrimmedLength(d)!=8){
                    timeinfected.setError("Please enter the correct format YYYYMMDD");
                    return;
                }
                int date = Integer.parseInt(d);
                Query query = ref2.orderByChild("dayTest").equalTo(date);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            int val;
                            for (int i = 0; i < factor[i].length(); i++){
                                int check = ds.child(factor[i]).getValue(Integer.class);
                                if (factorVal.size() != factor.length){
                                    factorVal.add(check);
                                }else{
                                    val = factorVal.get(i) + check;
                                    factorVal.set(i, val);

                                }

                            }

                        }

                        if (factorVal.size()!= 0 && xfactorVal.size()!=0) {
                            f.setText(Format.mostTable.getMostIndex(factorVal, xfactorVal).toString());
                            f1.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(factorVal.subList(0, 3)), new ArrayList<String>(xfactorVal.subList(0, 3))).toString());
                            f2.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(factorVal.subList(3, 7)), new ArrayList<String>(xfactorVal.subList(3, 7))).toString());
                            f3.setText(Format.mostTable.getMostIndex(new ArrayList<Integer>(factorVal.subList(7, 14)), new ArrayList<String>(xfactorVal.subList(7, 14))).toString());
                        } else {
                            f.setText(" No Result ");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    public static class LineAxisValueFormatter implements AxisValueFormatter {
        private ArrayList<String> mValues;


        public LineAxisValueFormatter(ArrayList<String> xVal) {
            this.mValues=xVal;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (value < mValues.size()) {
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
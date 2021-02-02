package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Form_RegisterIDR extends AppCompatActivity {
    Button register, find;
    EditText infected, diseased, recovered,  date;
    FirebaseDatabase database;
    DatabaseReference ref;
    String Date,i , d ,r;
    TextView description;
    ArrayList<String> exists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__register_i_d_r);

        register = (Button) findViewById(R.id.button_idr_register);
        infected = (EditText) findViewById(R.id.in_infected);
        diseased = (EditText) findViewById(R.id.in_diseased);
        recovered = (EditText) findViewById(R.id.in_recovered);
        date = (EditText) findViewById(R.id.in_idr_date);
        find = (Button) findViewById(R.id.btn_find_infected);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("idr");

        description = (TextView) findViewById(R.id.txt_regidr_description);
        description.setText("The data collected will be used for research purposes, you will be responsible for the information you submit. By submitting this form indicates agreement to the terms");


    }

    @Override
    protected void onStart() {
        super.onStart();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                 i = infected.getText().toString().trim();
                 d = diseased.getText().toString().trim();
                 r = recovered.getText().toString().trim();
                Date = date.getText().toString().trim();
                exists.clear();


                if (TextUtils.isEmpty(Date)) {
                    date.setError("Please fill in the full date");
                    return;
                }else if (TextUtils.getTrimmedLength(Date)!=8){
                    date.setError("Date format incorrect: YYYYMMDD ");
                    return;
                 }
                if (TextUtils.isEmpty(i)) {
                    infected.setError("Please fill in the infected amount for today");
                    return;
                }
                if (TextUtils.isEmpty(d)) {
                    diseased.setError("Please fill in the diseased amount for today");
                    return;
                }
                if (TextUtils.isEmpty(r)) {
                    recovered.setError("Please fill in the recovered amount for today");
                    return;
                }
                int check = Integer.parseInt(Date);
                Query query = ref.orderByChild("date").equalTo(check);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            date.setError("Suggested date contains a data");
                            return;
                        }else{
                            if (!TextUtils.isEmpty(i) && !TextUtils.isEmpty(d) && !TextUtils.isEmpty(r)) {
                                String id = ref.push().getKey();
                                Class_IDR idr = new Class_IDR(Integer.parseInt(i), Integer.parseInt(d), Integer.parseInt(r), Integer.parseInt(Date));
                                ref.child(id).setValue(idr);
                                Toast.makeText(Form_RegisterIDR.this, "New Register Added", Toast.LENGTH_LONG).show();
                                Intent launchActivity = new Intent(Form_RegisterIDR.this, UI_MainMenu.class);
                                startActivity(launchActivity);
                            } else {
                                Toast.makeText(Form_RegisterIDR.this, "Error", Toast.LENGTH_LONG).show();
                            }
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
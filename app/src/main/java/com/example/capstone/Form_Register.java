package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Form_Register extends AppCompatActivity {
    //Interface Var
    EditText other, location, daytest, comment;
    CheckBox indirect, contact, aerosol, unknown1, clustering, environmental, touch, hygiene, unknown2, local, international, visit, religion, sports, restaurant, work, unknown3;
    Button submit;
    DatabaseReference databaseFactors, databaseInfectedDetails;
    TextView description;

    //Others Information
    EditText pname,  race, nationality, condition, address, dname, hospital;
    EditText nric, cpn, phone, birthday, drID;
    RadioButton male, female, rns;
    String gender;
    String Pname, Race, Nationality, Condition, Address, Dname, Hospital, NRIC, CPN, Phone, Birthday, DRID;


    //Default Form Var
    String Location;
    String DayTest;

    int Indirect = 0, Contact = 0, Aerosol = 0;
    int Clustering = 0, Environmental = 0, Touch = 0, Hygiene= 0;
    int Local = 0, International = 0, Visit = 0, Religion = 0, Sports = 0, Restaurant = 0, Work = 0;
    int Primary = 0, Secondary = 0, Exposure = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register);
        //database
        databaseFactors = FirebaseDatabase.getInstance().getReference("infected");
        databaseInfectedDetails = FirebaseDatabase.getInstance().getReference("infected_details");

        description = (TextView)findViewById(R.id.txt_register_description);
        description.setText("The information provided in this form will be used for research purpose, you will be responsible for any information submitted from this form. Submitting indicates agreement to this regulations");

        //question 1 variables
        indirect= (CheckBox)findViewById(R.id.cb1_primary);
        contact=(CheckBox)findViewById(R.id.cb2_primary);
        aerosol=(CheckBox)findViewById(R.id.cb3_primary);
        unknown1= (CheckBox)findViewById(R.id.cb4_primary);

        //question 2 variables
        clustering = (CheckBox)findViewById(R.id.cb1_secondary);
        environmental=(CheckBox)findViewById(R.id.cb2_secondary);
        touch=(CheckBox)findViewById(R.id.cb3_secondary);
        hygiene=(CheckBox)findViewById(R.id.cb4_secondary);
        unknown2= (CheckBox)findViewById(R.id.cb5_secondary);

        //question 3 variables
        local=(CheckBox)findViewById(R.id.cb1_exposure);
        international= (CheckBox)findViewById(R.id.cb2_exposure);
        visit=(CheckBox)findViewById(R.id.cb3_exposure);
        religion = (CheckBox)findViewById(R.id.cb4_exposure);
        sports = (CheckBox)findViewById(R.id.cb5_exposure);
        restaurant = (CheckBox)findViewById(R.id.cb6_exposure);
        work = (CheckBox)findViewById(R.id.cb7_exposure);
        unknown3 = (CheckBox)findViewById(R.id.cb8_exposure);

        //Others
        other = (EditText)findViewById(R.id.in_other);
        location=(EditText)findViewById(R.id.in_location);
        daytest=(EditText)findViewById(R.id.in_daytest);
        comment = (EditText)findViewById(R.id.in_comment);

        //additional information
        pname = (EditText)findViewById(R.id.in_pname);
        race = (EditText) findViewById(R.id.in_prace);
        nationality = (EditText)findViewById(R.id.in_pnationality);
        condition = (EditText) findViewById(R.id.in_pcondition);
        address = (EditText) findViewById(R.id.in_padd);
        dname = (EditText) findViewById(R.id.in_dname);
        hospital = (EditText)findViewById(R.id.in_hospital);
        nric = (EditText) findViewById(R.id.in_pnric);
        cpn = (EditText) findViewById(R.id.in_cpn);
        phone = (EditText)findViewById(R.id.in_pcontact);
        birthday = (EditText)findViewById(R.id.in_pbday);
        drID = (EditText) findViewById(R.id.in_doctorid);


        male=(RadioButton)findViewById(R.id.rbtn1_pgender);
        female=(RadioButton)findViewById(R.id.rbtn2_pgender);
        rns=(RadioButton)findViewById(R.id.rbtn3_pgender);


        submit = (Button)findViewById(R.id.btn_submit);



        //Check empty field
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location = location.getText().toString().trim();
                DayTest = daytest.getText().toString().trim();

                 Pname = pname.getText().toString().trim();
                 Race = race.getText().toString().trim();
                 Nationality = nationality.getText().toString().trim();
                 Condition = condition.getText().toString().trim();
                 Address = address.getText().toString().trim();
                 Dname = dname.getText().toString().trim();
                 Hospital = hospital.getText().toString().trim();
                 NRIC = nric.getText().toString().trim();
                 CPN = cpn.getText().toString().trim();
                 Phone = phone.getText().toString().trim();
                 Birthday = birthday.getText().toString().trim();
                 DRID = drID.getText().toString().trim();

                String[] StringCheck = {Pname, Race, Nationality, Condition, Address, Dname, Hospital, NRIC, CPN, Phone, Birthday, DRID};

                for (int i = 0; i < StringCheck.length; i++) {
                    if (TextUtils.isEmpty(StringCheck[i])) {
                        Toast.makeText(Form_Register.this, "Please fill in the empty fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (male.isChecked()) {
                    gender = "male";
                } else if (female.isChecked()) {
                    gender = "female";
                } else if (rns.isChecked()) {
                    gender = "rather not say";
                } else {
                    Toast.makeText(Form_Register.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }


                //primary factor value + exception handling
                if (indirect.isChecked()) {
                    Indirect = 1;
                } else {
                    Indirect = 0;
                }
                if (contact.isChecked()) {
                    Contact = 1;
                } else {
                    Contact = 0;
                }
                if (aerosol.isChecked()) {
                    Aerosol = 1;
                } else {
                    Aerosol = 0;
                }
                if (!indirect.isChecked() && !contact.isChecked() && !aerosol.isChecked() && !unknown1.isChecked()) {
                    Toast.makeText(Form_Register.this, "Please select primary factor", Toast.LENGTH_SHORT).show();
                } else {
                    Primary = 1;
                }

                //secondary factor value + exception handling
                if (clustering.isChecked()) {
                    Clustering = 1;
                } else {
                    Clustering = 0;
                }
                if (environmental.isChecked()) {
                    Environmental = 1;
                } else {
                    Environmental = 0;
                }
                if (touch.isChecked()) {
                    Touch = 1;
                } else {
                    Touch = 0;
                }
                if (hygiene.isChecked()) {
                    Hygiene = 1;
                } else {
                    Hygiene = 0;
                }
                if (!clustering.isChecked() && !environmental.isChecked() && !touch.isChecked() && !hygiene.isChecked() && !unknown2.isChecked()) {
                    Toast.makeText(Form_Register.this, "Please select secondary factor", Toast.LENGTH_SHORT).show();
                } else {
                    Secondary = 1;
                }

                //exposure value + exception handling
                if (local.isChecked()) {
                    Local = 1;
                } else {
                    Local = 0;
                }
                if (international.isChecked()) {
                    International = 1;
                } else {
                    International = 0;
                }
                if (visit.isChecked()) {
                    Visit = 1;
                } else {
                    Visit = 0;
                }
                if (religion.isChecked()) {
                    Religion = 1;
                } else {
                    Religion = 0;
                }
                if (sports.isChecked()) {
                    Sports = 1;
                } else {
                    Sports = 0;
                }
                if (restaurant.isChecked()) {
                    Restaurant = 1;
                } else {
                    Restaurant = 0;
                }
                if (work.isChecked()) {
                    Work = 1;
                } else {
                    Work = 0;
                }
                if (!local.isChecked() && !international.isChecked() && !visit.isChecked() && !religion.isChecked() && !sports.isChecked() && !restaurant.isChecked() && !work.isChecked() && !unknown3.isChecked()) {
                    Toast.makeText(Form_Register.this, "Please select exposure option", Toast.LENGTH_SHORT).show();
                } else {
                    Exposure = 1;
                }

                //other questions that are required
                if (TextUtils.isEmpty(Location)) {
                    location.setError("Possible Location Is Required.");
                    return;
                }
                if (TextUtils.isEmpty(DayTest)) {
                    daytest.setError("Day test required");
                    return;
                } else if (TextUtils.getTrimmedLength(DayTest) != 8) {
                    daytest.setError("Date format incorrect: YYYYMMDD ");
                }

                Query query = databaseInfectedDetails.orderByChild("cpn").equalTo(CPN);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            cpn.setError("Suggested patient contains a data");
                            Toast.makeText(Form_Register.this, "CPN already registered", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if (Primary == 1 && Secondary == 1 && Exposure == 1 && !TextUtils.isEmpty(Location) && !TextUtils.isEmpty(DayTest)){
                            String id = databaseFactors.push().getKey();
                            Class_Infected infected = new Class_Infected(Contact, Indirect, Aerosol, Clustering, Environmental, Touch, Hygiene, Local, International, Visit, Religion, Sports, Restaurant, Work, Location, Integer.parseInt(DayTest));
                            databaseFactors.child(id).setValue(infected);
                            Class_InfectedDetails idetails = new Class_InfectedDetails(Pname, gender, Race, Nationality, Condition, Address, Dname, Hospital, NRIC, CPN, Phone, Birthday, DRID);
                            databaseInfectedDetails.child(id).setValue(idetails);
                            Toast.makeText(Form_Register.this, "New Register Added", Toast.LENGTH_LONG).show();
                            Intent launchActivity = new Intent(Form_Register.this, UI_MainMenu.class);
                            startActivity(launchActivity);
                        }

                    }

                    //Assign to database


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Form_Questionnaire extends AppCompatActivity {
        EditText most, suggestion;
        RadioButton qindirect, qcontact, qaerosol, qclustering, qenvironmental, qtouch, qhygiene, qlocal, qinternational, qvisit, qreligion, qsports, qrestaurant, qwork;
        Button qsubmit;
        DatabaseReference databaseResponse, databaseDrResponse, databaseUser, databaseResponseDetails;
        TextView description;

        //Others
        EditText name, race, nationality, email, fsuggestion, feedbacks, birthday, phone;
        String Name, Race, Nationality, Email, Fsuggestion, Feedbacks, Birthday, Phone;

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        //Default Form Var
        int Indirect = 0, Contact = 0, Aerosol = 0;
        int Clustering = 0, Environmental = 0, Touch = 0, Hygiene = 0;
        int Local = 0, International = 0, Visit = 0, Religion = 0, Sports = 0, Restaurant = 0, Work = 0;
        int Primary = 0, Secondary = 0, Exposure = 0;
        String Most, Suggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_questionnaire);

        databaseResponse = FirebaseDatabase.getInstance().getReference("response");
        databaseUser =  FirebaseDatabase.getInstance().getReference("user");
        databaseDrResponse = FirebaseDatabase.getInstance().getReference("drresponse");
        databaseResponseDetails = FirebaseDatabase.getInstance().getReference("response_details");

        description = (TextView) findViewById(R.id.txt_questionnaire_description);
        description.setText("Information provided will be strictly use for research purpose only, submitting the questionnaire is an indication of agreement to this terms");
        //Question 1 Primary
        qindirect = (RadioButton) findViewById(R.id.rbtn1_primary);
        qcontact = (RadioButton) findViewById(R.id.rbtn2_primary);
        qaerosol = (RadioButton) findViewById(R.id.rbtn3_primary);

        //Question 2 Secondary
        qclustering = (RadioButton) findViewById(R.id.rbtn1_secondary);
        qenvironmental = (RadioButton) findViewById(R.id.rbtn2_secondary);
        qtouch = (RadioButton) findViewById(R.id.rbtn3_secondary);
        qhygiene = (RadioButton) findViewById(R.id.rbtn4_secondary);

        //Question 3 Exposure
        qlocal = (RadioButton) findViewById(R.id.rbtn1_exposure);
        qinternational = (RadioButton) findViewById(R.id.rbtn2_exposure);
        qvisit = (RadioButton) findViewById(R.id.rbtn3_exposure);
        qreligion = (RadioButton) findViewById(R.id.rbtn4_exposure);
        qsports = (RadioButton) findViewById(R.id.rbtn5_exposure);
        qrestaurant = (RadioButton) findViewById(R.id.rbtn6_exposure);
        qwork = (RadioButton) findViewById(R.id.rbtn7_exposure);



        //Text inputs
        most = (EditText) findViewById(R.id.in_most);
        suggestion = (EditText)findViewById(R.id.in_suggestion2);
        feedbacks = (EditText)findViewById(R.id.in_feedbacks);

        //Button
        qsubmit = (Button) findViewById(R.id.btn_qsubmit);

        name = (EditText) findViewById(R.id.in_fname);
        race = (EditText) findViewById(R.id.in_race);
        nationality = (EditText) findViewById(R.id.in_nationality);
        email = (EditText) findViewById(R.id.in_email);
        fsuggestion = (EditText) findViewById(R.id.in_fsuggestion);
        feedbacks = (EditText) findViewById(R.id.in_feedbacks);
        birthday = (EditText) findViewById(R.id.in_birthday);
        phone = (EditText) findViewById(R.id.in_phone);



        qsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Most = most.getText().toString().trim();
                Suggestion = suggestion.getText().toString().trim();

                Name = name.getText().toString().trim();
                Race = race.getText().toString().trim();
                Nationality = nationality.getText().toString().trim();
                Email = email.getText().toString().trim();
                Fsuggestion = fsuggestion.getText().toString().trim();
                Feedbacks = feedbacks.getText().toString().trim();
                Birthday = birthday.getText().toString().trim();
                Phone = phone.getText().toString().trim();

                String [] NullCheck = {Name, Race, Nationality, Email, Fsuggestion, Feedbacks, Birthday, Phone};
                for (int i = 0; i < NullCheck.length; i++){
                    if (TextUtils.isEmpty(NullCheck[i])){
                        Toast.makeText(Form_Questionnaire.this , "Please fill in the empty field", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }


                //primary factor value + exception handling
                if (qindirect.isChecked()) {
                    Indirect = 1;
                } else {
                    Indirect = 0;
                }
                if (qcontact.isChecked()) {
                    Contact = 1;
                } else {
                    Contact = 0;
                }
                if (qaerosol.isChecked()) {
                    Aerosol = 1;
                } else {
                    Aerosol = 0;
                }
                if (!qindirect.isChecked() && !qcontact.isChecked() && !qaerosol.isChecked()) {
                    Toast.makeText(Form_Questionnaire.this, "Please select one or more primary factor", Toast.LENGTH_SHORT).show();
                } else {
                    Primary = 1;
                }

                //secondary factor value + exception handling
                if (qclustering.isChecked()) {
                    Clustering = 1;
                } else {
                    Clustering = 0;
                }
                if (qenvironmental.isChecked()) {
                    Environmental = 1;
                } else {
                    Environmental = 0;
                }
                if (qtouch.isChecked()) {
                    Touch = 1;
                } else {
                    Touch = 0;
                }
                if (qhygiene.isChecked()) {
                    Hygiene = 1;
                } else {
                    Hygiene = 0;
                }
                if (!qclustering.isChecked() && !qenvironmental.isChecked() && !qtouch.isChecked() && !qhygiene.isChecked()) {
                    Toast.makeText(Form_Questionnaire.this, "Please select one or more secondary factor", Toast.LENGTH_SHORT).show();
                } else {
                    Secondary = 1;
                }

                //exposure value + exception handling
                if (qlocal.isChecked()) {
                    Local = 1;
                } else {
                    Local = 0;
                }
                if (qinternational.isChecked()) {
                    International = 1;
                } else {
                    International = 0;
                }
                if (qvisit.isChecked()) {
                    Visit = 1;
                } else {
                    Visit = 0;
                }
                if (qreligion.isChecked()) {
                    Religion = 1;
                } else {
                    Religion = 0;
                }
                if (qsports.isChecked()) {
                    Sports = 1;
                } else {
                    Sports = 0;
                }
                if (qrestaurant.isChecked()) {
                    Restaurant = 1;
                } else {
                    Restaurant = 0;
                }
                if (qwork.isChecked()) {
                    Work = 1;
                } else {
                    Work = 0;
                }
                if (!qlocal.isChecked() && !qinternational.isChecked() && !qvisit.isChecked() && !qreligion.isChecked()&& !qsports.isChecked() && !qrestaurant.isChecked()&& !qwork.isChecked()) {
                    Toast.makeText(Form_Questionnaire.this, "Please select one or more exposure option", Toast.LENGTH_SHORT).show();
                } else {
                    Exposure = 1;
                }



                if (Primary == 1 && Secondary == 1 && Exposure == 1 && !TextUtils.isEmpty(Most) && !TextUtils.isEmpty(Suggestion)){
                    Query query = databaseUser.orderByKey().equalTo(user.getUid());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String check = dataSnapshot.child(user.getUid()).child("userType").getValue(String.class);
                                if (check.equals("Doctor")){
                                    String id = user.getUid();
                                    Class_Response drresponse = new Class_Response(Indirect, Contact, Aerosol, Clustering, Environmental, Touch, Hygiene, Local, International, Visit, Religion, Sports, Restaurant, Work, Most, Suggestion);
                                    databaseDrResponse.child(id).setValue(drresponse);
                                    Class_RespsonseDetails ressponsedetails = new Class_RespsonseDetails(Name , Race, Nationality, Email, Fsuggestion, Feedbacks, Birthday, Phone);
                                    databaseResponseDetails.child(id).setValue(ressponsedetails);
                                    databaseUser.child(user.getUid()).child("qCount").setValue(0);
                                    Toast.makeText(Form_Questionnaire.this, "Response submitted", Toast.LENGTH_LONG).show();
                                    Intent launchActivity= new Intent(Form_Questionnaire.this, UI_MainMenu.class);
                                    startActivity(launchActivity);

                                    //databaseUser.child("qCount").setValue(0);

                                }
                                else if (check.equals("User")){
                                    String id = user.getUid();
                                    Class_Response response = new Class_Response(Indirect, Contact, Aerosol, Clustering, Environmental, Touch, Hygiene, Local, International,Religion, Sports, Restaurant, Work, Visit, Most, Suggestion);
                                    databaseResponse.child(id).setValue(response);
                                    String i = databaseResponseDetails.push().getKey();
                                    Class_RespsonseDetails ressponsedetails = new Class_RespsonseDetails(Name , Race, Nationality, Email, Fsuggestion, Feedbacks, Birthday, Phone);
                                    databaseResponseDetails.child(i).setValue(ressponsedetails);
                                    databaseUser.child(user.getUid()).child("qCount").setValue(0);
                                    Toast.makeText(Form_Questionnaire.this, "Response submitted", Toast.LENGTH_LONG).show();
                                    Intent launchActivity= new Intent(Form_Questionnaire.this, UI_MainMenu.class);
                                    startActivity(launchActivity);

                                    //databaseUser.child("qCount").setValue(0);
                                }
                                else{
                                    Toast.makeText(Form_Questionnaire.this, "An Error has Occurred", Toast.LENGTH_SHORT).show();
                                }

                            }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }else{
                    Toast.makeText(Form_Questionnaire.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
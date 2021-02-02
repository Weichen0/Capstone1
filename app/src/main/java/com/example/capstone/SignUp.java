package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    // call variables
    EditText Fname, Email , Password, CPassword;
    Button SignUp, Cancel;
    FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //link variables to button id
        Fname = findViewById(R.id.in_fname);
        Email = findViewById(R.id.in_email);
        Password = findViewById(R.id.input_password);
        CPassword = findViewById(R.id.input_cpassword);
        SignUp = findViewById(R.id.button_signup);
        Cancel = findViewById(R.id.button_cancel);
        databaseUser = FirebaseDatabase.getInstance().getReference("Class_User");

        mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), SignIn.class));
//            finish();
//        }

        // Sign Up Button
        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String cpassword = CPassword.getText().toString().trim();
                final String fname = Fname.getText().toString().trim();

                // Exception Handling
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email Is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    Password.setError("Password Must be Longer Than 6 Characters");
                    return;
                }
                if(!password.equals(cpassword)){
                    CPassword.setError("Passwords are different");
                    return;
                }
                //Register to Firebase and Result Message
                mAuth.createUserWithEmailAndPassword (email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(com.example.capstone.SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), SignIn.class));
                                String userType = "User";
                                Class_User user = new Class_User(userType, fname, 1);
                                FirebaseDatabase.getInstance().getReference("user")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user);
                                }
                            else{
                                Toast.makeText(com.example.capstone.SignUp.this, "Error! " +  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });

        // Cancel button
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
            }
        });
    }
}
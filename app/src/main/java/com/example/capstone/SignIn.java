package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    EditText Email, Password;
    Button Login, SignUp;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Email= (EditText)findViewById(R.id.in_signin_email);
        Password= (EditText)findViewById(R.id.input_sigin_password);
        Login= (Button)findViewById(R.id.button_login);
        SignUp= (Button)findViewById(R.id.button_signup);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser u = mAuth.getCurrentUser();

        //Startup ensure no user is logged in
        if (u != null){
            mAuth.signOut();
        }



            Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                // Exception Handling
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email Is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required.");
                    return;
                }
                // Authenticate Class_User
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignIn.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UI_MainMenu.class));
                        }else{
                            Toast.makeText(SignIn.this, "Error! " +  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
            
        // Sign Up Button
        SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View V3){
                Intent launchActivity= new Intent(SignIn.this, com.example.capstone.SignUp.class);
                startActivity(launchActivity);
            }
        });
    }

   
}
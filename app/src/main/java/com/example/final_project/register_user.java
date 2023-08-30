package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register_user extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner,registerUser;
    private EditText editTextFullName, editTextAge, editTextTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        banner = (TextView)findViewById(R.id.banner);
        registerUser = (Button)findViewById(R.id.registerUser);
        banner.setOnClickListener((View.OnClickListener) this);
        registerUser.setOnClickListener((View.OnClickListener) this);
        editTextFullName=(EditText)findViewById(R.id.fullName);
        editTextAge=(EditText)findViewById(R.id.age);
        editTextTextEmail=(EditText)findViewById(R.id.email);
        editTextPassword=(EditText)findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, Register_Page.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String email = editTextTextEmail.getText().toString().trim();
        Log.d("Adviks",email);
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String Age = editTextAge.getText().toString().trim();
        if(fullName.isEmpty())
        {
            editTextFullName.setError("Full Name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if(Age.isEmpty())
        {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editTextTextEmail.setError("Email is required!");
            editTextTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextTextEmail.setError("Please provide valid email!");
            editTextTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(fullName, Age, email);
                  DatabaseReference database =  FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            database.child("fullName").setValue(fullName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                database.child("age").setValue(Age);
                                database.child("email").setValue(email);
                                Toast.makeText(register_user.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(register_user.this,Register_Page.class));
                            } else {
                                Toast.makeText(register_user.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(register_user.this,Register_Page.class));

                            }
                        }
                    });

                } else {
                    Toast.makeText(register_user.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
            });

    }
}
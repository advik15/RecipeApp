package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class account_info extends AppCompatActivity {
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private String id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        logout = findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(account_info.this, Register_Page.class));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        id=user.getUid();
        final TextView greetingTextView = findViewById(R.id.greeting);
        final TextView fullNameTextView = findViewById(R.id.fullName);
        final TextView emailTextView = findViewById(R.id.emailAddress);
        final TextView ageTextView = findViewById(R.id.age);

        myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String age = userProfile.age;
                    greetingTextView.setText("Welcome " + fullName + "!");
                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(account_info.this,"Something wrong happened!",Toast.LENGTH_LONG).show();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
     bottomNavigationView.setSelectedItemId(R.id.account_info);
        getSupportFragmentManager().beginTransaction().replace(R.id.constraintlayout,new Food_News_Fragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;
                switch(item.getItemId()){
                    case R.id.recipesFragment:
                        startActivity(new Intent(getApplicationContext(),RecipeMaker.class));
                        finish();
                        break;
                    case R.id.fav_Recipes_Fragment:

                        startActivity(new Intent(getApplicationContext(),FavoriteRecipes.class));
                        finish();
                        break;
                    case R.id.food_News_Fragment:
                        selected = new Food_News_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraintlayout,selected).commit();

                        break;
                    case R.id.account_info:
                        startActivity(new Intent(getApplicationContext(),account_info.class));
                        finish();
                        break;
                }

                return true;
            }
        });

    }
}
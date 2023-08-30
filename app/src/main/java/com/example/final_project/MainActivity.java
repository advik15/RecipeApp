package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        //  bottomNavigationView.setSelectedItemId(R.id.recipesFragment);
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
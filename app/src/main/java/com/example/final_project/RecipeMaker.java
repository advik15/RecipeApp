package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
import java.util.ArrayList;

public class RecipeMaker extends AppCompatActivity {
    TextView t1;
    ImageView imageView2;
    String src;
    EditText editText;
    String ingredients;
    ImageButton searchButton;
    RecyclerView recyclerView;
    recipesAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Recipe_Maker_Get> list= new ArrayList<Recipe_Maker_Get>();

    class Task extends AsyncTask<String, Void, String> {

        String a = "";
        String recipe = "";
        String summary = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://api.spoonacular.com/recipes/complexSearch?apiKey=b571d6eb3098401ea25833da933bcf0c&query="+ingredients+"&addRecipeInformation=true" );
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                InputStream stream;
                stream = connect.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                a = bufferedReader.readLine();


            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();                Log.d("Error 1", "hi");

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Error 2", "hi");

            }
            catch(NullPointerException nullPointerException){
                nullPointerException.printStackTrace();
                Log.d("Error 4", nullPointerException.getMessage());

            }

            return a;

        }

        @Override
        protected void onPostExecute(String s){
            try {
                JSONObject rootObject = new JSONObject(s);
                JSONArray array = rootObject.getJSONArray("results");
                for(int i = 0;i<array.length();i++) {
                    recipe = array.getJSONObject(i).getString("title");
                    //  t1.setText(recipe);
                    src = array.getJSONObject(i).getString("image");
                    summary = array.getJSONObject(i).getString("summary");
                    summary =  summary.replaceAll("<b>","");
                    summary =  summary.replaceAll("</b>","");
                    summary =   summary.replaceAll("<a>","");
                    summary=    summary.replaceAll("</a>","");
                    summary =   summary.replaceAll("href=","</a>");
                    summary = summary.replaceAll(">","");
                    summary=    summary.replaceAll("</a","");
                    summary=    summary.replaceAll("<a","");

                    list.add(new Recipe_Maker_Get(recipe,src,summary));
                    adapter = new recipesAdapter(list,RecipeMaker.this);
                    linearLayoutManager = new LinearLayoutManager(RecipeMaker.this,RecyclerView.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);

                }


                Log.d("FIND_IT", src);
                //Picasso.get().load(src).into(imageView2);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Error 3", "hi");

            }
            catch(NullPointerException nullPointerException){
                nullPointerException.printStackTrace();
                Log.d("Error 4", nullPointerException.getMessage());

            }

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipes);

        t1 = findViewById(R.id.textView5);
        imageView2 = findViewById(R.id.imageView2);
        editText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recylerview2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ingredients = String.valueOf(editText.getText());

            }
        });
        Log.d("Adviksac","careers"+ingredients);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.execute();
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        //  bottomNavigationView.setSelectedItemId(R.id.recipesFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.constraintlayout, new Food_News_Fragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected = null;
                switch (item.getItemId()) {
                    case R.id.recipesFragment:
                        startActivity(new Intent(getApplicationContext(), RecipeMaker.class));
                        finish();
                        break;

                    case R.id.fav_Recipes_Fragment:
                        startActivity(new Intent(getApplicationContext(), FavoriteRecipes.class));
                        finish();
                        break;
                    case R.id.food_News_Fragment:
                        selected = new Food_News_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.constraintlayout, selected).commit();

                        break;
                    case R.id.account_info:
                        startActivity(new Intent(getApplicationContext(), account_info.class));
                        finish();
                        break;
                }
                return true;
            }
        });


    }
}
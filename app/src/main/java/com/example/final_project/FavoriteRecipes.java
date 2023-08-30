package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class FavoriteRecipes extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference myRef;
    private String userID;
    ArrayList<Recipe_Maker_Get> list;
    RecyclerView recyclerView;
    recipesAdapter2 adapter;
    LinearLayoutManager linearLayoutManager;
    String imageLink;
    String recipetitle;
    String summary;
    int i;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fav__recipes_);
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        recyclerView=findViewById(R.id.recylerview2);
        i = 0;
        list = new ArrayList<Recipe_Maker_Get>();
        imageLink="";
        recipetitle = "";
        summary="";
        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {


                    Iterable<DataSnapshot> snapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        if (next.child("imageLink").getValue() != null) {
                            imageLink = next.child("imageLink").getValue().toString();
                        }
                        if (next.child("title").getValue() != null) {
                            recipetitle = next.child("title").getValue().toString();
                        }
                        if (next.child("summary").getValue() != null) {
                            summary = next.child("summary").getValue().toString();
                            Log.d("TAG_INFO2", summary);
                            if (next.child("imageLink").getValue() == null) {
                                Toast.makeText(FavoriteRecipes.this, "No favorite recipes!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }catch (NullPointerException nullPointerException)
                {
                    nullPointerException.printStackTrace();
                    Toast.makeText(FavoriteRecipes.this, "No favorite recipes!", Toast.LENGTH_LONG).show();

                }
                catch (IllegalArgumentException illegalArgumentException)
                {
                    illegalArgumentException.printStackTrace();
                    Toast.makeText(FavoriteRecipes.this, "No favorite recipes!", Toast.LENGTH_LONG).show();

                }
                    list.add(new Recipe_Maker_Get(recipetitle,imageLink,summary));




                if(list.size()>1) {
                    for (int i = 1; i < list.size(); i++) {

                        if (list.get(i).getImageLink().equals(list.get(i-1).getImageLink())) {
                            list.remove(i-1);
                            i--;


                        }
                    }
                }
                adapter = new recipesAdapter2(list,FavoriteRecipes.this);

                linearLayoutManager = new LinearLayoutManager(FavoriteRecipes.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
                Log.i("TAG", imageLink);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query = myRef.child(userID).orderByKey();
        query.addListenerForSingleValueEvent(queryValueListener);


      /*  reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Recipe_Maker_Get maker = snapshot.getValue(Recipe_Maker_Get.class);
                t1.setText(String.valueOf(maker.title));
                if(maker!=null)
                {
                    String recipetitle = maker.title;
                    String imageLink = maker.imageLink;
                    String summary = maker.summary;

                    list.add(new Recipe_Maker_Get(recipetitle,imageLink,summary));
                    //  Log.d("TAG_INFO",String.valueOf(list.get(Integer.parseInt(imageLink))));
                    adapter = new recipesAdapter2(list,FavoriteRecipes.this);
                    linearLayoutManager = new LinearLayoutManager(FavoriteRecipes.this,RecyclerView.VERTICAL,false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FavoriteRecipes.this,"Something wrong happened!",Toast.LENGTH_LONG).show();

            }
        });

       */
       BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
       bottomNavigationView.setSelectedItemId(R.id.fav_Recipes_Fragment);
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
package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

public class recipesAdapter2 extends RecyclerView.Adapter<recipesAdapter2.RecyclerViewHolder> {
private Context parentContext;
   Recipe_Maker_Get maker;
    Recipe_Maker_Get recipe;
    int index;
   int i;
    private ArrayList<Recipe_Maker_Get> list;
    public recipesAdapter2(ArrayList<Recipe_Maker_Get> list, FavoriteRecipes context){
        parentContext=context;
        this.list = list;
        i = 1;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentContext).inflate(R.layout.holder2, parent,false);
        recipesAdapter2.RecyclerViewHolder holder = new recipesAdapter2.RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        try {
            Recipe_Maker_Get recipe = list.get(position);
            holder.title.setText(recipe.getTitle());
            holder.summary.setText(recipe.getSummary());
            Picasso.get().load(recipe.getImageLink()).into(holder.imageView);
            maker = new Recipe_Maker_Get(recipe.getTitle(),recipe.getImageLink(),recipe.getSummary());
            Log.d("TAG_INFO3", maker.getImageLink());

        }catch (IllegalArgumentException illegalArgumentException)
        {
            illegalArgumentException.printStackTrace();
            Toast.makeText(parentContext, "No favorite recipes!", Toast.LENGTH_LONG).show();


        }
        catch (NullPointerException nullPointerException)
        {
            nullPointerException.printStackTrace();
            Toast.makeText(parentContext, "No favorite recipes!", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;
        TextView summary;
        ImageButton imageButton;
        FirebaseUser user;
        private DatabaseReference reference;
        private String userId;
        public RecyclerViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            title = itemView.findViewById(R.id.textView5);

            summary = itemView.findViewById(R.id.textView4);
            imageButton = itemView.findViewById(R.id.imageButton2);
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(parentContext,"Recipe Unfavorited!",Toast.LENGTH_LONG).show();

                 Query query = reference.orderByChild("title").equalTo(recipe.getTitle());

                    Log.d("TAG","" + recipe.getTitle());
                 query.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         for(DataSnapshot appleSnapshot: snapshot.getChildren())
                         {
                             Log.d("TAG1","" + appleSnapshot.getRef().removeValue());

                             appleSnapshot.getRef().removeValue();
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
                    Query query2 = reference.child("Users").orderByChild("imageLink").equalTo(recipe.getImageLink());
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot appleSnapshot: snapshot.getChildren())
                            {
                                appleSnapshot.getRef().removeValue();
                      //          Toast.makeText(parentContext,"Recipe Unfavorited!",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                         //   Toast.makeText(parentContext,"Something went wrong! Try again",Toast.LENGTH_LONG).show();

                        }
                    });
                    Query query3 = reference.child("Users").orderByChild("summary").equalTo(recipe.getSummary());
                    query3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot appleSnapshot: snapshot.getChildren())
                            {
                                appleSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    }
            });
        }
    }

}
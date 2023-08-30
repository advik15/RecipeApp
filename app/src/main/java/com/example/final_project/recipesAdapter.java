package com.example.final_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class recipesAdapter extends RecyclerView.Adapter<recipesAdapter.RecyclerViewHolder>{
   private Context parentContext;
    int i;

    Recipe_Maker_Get maker;
    private ArrayList<Recipe_Maker_Get> list;
    public recipesAdapter(ArrayList<Recipe_Maker_Get> list, RecipeMaker context){
        parentContext=context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //sets XML
        View view = LayoutInflater.from(parentContext).inflate(R.layout.holder_view, parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Recipe_Maker_Get recipe = list.get(position);
        holder.title.setText(recipe.getTitle());
        holder.summary.setText(recipe.getSummary());
        Picasso.get().load(recipe.getImageLink()).into(holder.imageView);
        maker = new Recipe_Maker_Get(recipe.getTitle(),recipe.getImageLink(),recipe.getSummary());
        Log.d("TAG_INFO4", maker.getImageLink());



    }

    @Override
    public int getItemCount() {
        //current size of list
        return list.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        //sets up all views using findViewByID

       TextView title;
       ImageView imageView;
       TextView summary;
       ImageButton imageButton;
       FirebaseUser user;
        private DatabaseReference reference;
        private String userID;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView2);
            title = itemView.findViewById(R.id.textView5);

            summary = itemView.findViewById(R.id.textView4);
            imageButton = itemView.findViewById(R.id.imageButton);
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            DatabaseReference myRef;
            userID=user.getUid();
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    reference.push().setValue(maker).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          if(task.isSuccessful()){
                            Toast.makeText(parentContext, "Favorited!", Toast.LENGTH_LONG).show();}
else {
                                Toast.makeText(parentContext, "Failed to favorite! Try again!", Toast.LENGTH_LONG).show();


                            }
                        }
                    });

                }
                   });


        }
    }
}

package com.example.mynewsapp.viewmodel;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mynewsapp.ui.NewsDetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsDetailsActivityViewModel {



    public void shareNews(String url, NewsDetailsActivity newsDetailsActivity) {

        try{

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plan");
            i.putExtra(Intent.EXTRA_SUBJECT, "My News App");
            String body = "News" + "\n" + url + "\n" + "Share from the News App" + "\n";
            i.putExtra(Intent.EXTRA_TEXT, body);
           newsDetailsActivity.startActivity(Intent.createChooser(i, "Share with :"));

        }catch (Exception e){
        }
    }
    public void updateInfirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("news_seen_count");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long value = (long) dataSnapshot.getValue();
                value = value + 1;
                dataSnapshot.getRef().setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }

        });

    }

    public void updateLikeInFirebase(final NewsDetailsActivity newsDetailsActivity) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("news_liked_count");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long value = (long) dataSnapshot.getValue();
                value = value + 1;
                dataSnapshot.getRef().setValue(value);

                Toast.makeText(newsDetailsActivity,"Value Updated Thanks",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("","");

            }

        });
    }
}

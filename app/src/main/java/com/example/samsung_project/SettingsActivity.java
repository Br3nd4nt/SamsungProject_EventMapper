package com.example.samsung_project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    EditText nameChanging;
    CircleImageView image;
    DatabaseReference ref;
    FirebaseAuth auth;
    FirebaseUser user;
    String currentUserID;
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        nameChanging = findViewById(R.id.changing_name);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        currentUserID = auth.getCurrentUser().getUid();
        ref.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameChanging.setText(dataSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        image = (CircleImageView) findViewById(R.id.profile_image);
        /*
        if(user != null) {
            currentUserID = user.getUid();
        }
        */
         findViewById(R.id.save_changes).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ref.child("Users").child(currentUserID).child("name").setValue(nameChanging.getText().toString());
             }
         });
            /*
            ref.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChild("name") && dataSnapshot.hasChild("image")) {

                        String userName = dataSnapshot.child("name").getKey().toString();
                        nameChanging.setText(userName);


                    } else if (dataSnapshot.exists() && dataSnapshot.hasChild("name")) {

                        String userName = dataSnapshot.child("name").getKey().toString();
                        nameChanging.setText(userName);

                    } else {
                        Toast.makeText(getApplicationContext(), "Пожалуйста, введите имя", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            */



    }
}
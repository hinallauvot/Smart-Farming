package com.example.hinal.smartfarming;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private Toolbar mToolbar;
    TextView textView;
    Typeface typeface;
    ImageView BackImage;
    LinearLayout linearLayout;
    Animation fromBottom;
    CardView queries, plans, about, profile;
    List<ModelActivatedPlans> listActive = new ArrayList<ModelActivatedPlans>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.textHead);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        BackImage = findViewById(R.id.bgapp);
        linearLayout = findViewById(R.id.menus);


       queries = findViewById(R.id.cd_queries);
       plans = findViewById(R.id.cd_plans);
       profile = findViewById(R.id.cd_profile);
       about = findViewById(R.id.cd_about);


        textView.animate().translationY(80).setDuration(800).setStartDelay(300);
        queries.startAnimation(fromBottom);
        plans.startAnimation(fromBottom);
        profile.startAnimation(fromBottom);
        about.startAnimation(fromBottom);



        //On click listner for grid items


        queries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, ChatRecyclerViewActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });


        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, ActPlanActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });


    }

    @Override

    public void onStart() {

        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();



        if(currentUser == null){



            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startIntent);

            finish();




        }



    }





}
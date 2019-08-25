package com.example.hinal.smartfarming;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    Toolbar mToolbar;
    DatabaseReference mDatabase;
    Button mLogout;
    ShimmerFrameLayout shimmerFrameLayout;
    TextView mName,Phone,Email,District,TotalLand,CultiLand;
    String ProfileName,mPhone,mEmail,mDistrict,mTotalLand,mCultiLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mToolbar = findViewById(R.id.profile_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        mName = findViewById(R.id.tvName);
        Phone = findViewById(R.id.tvPhone);
        District= findViewById(R.id.tvDistrict);
        Email = findViewById(R.id.tvEmail);
        TotalLand = findViewById(R.id.tvTotalLand);
        CultiLand = findViewById(R.id.tvCultiLand);

        mLogout = findViewById(R.id.logoutBtn);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Personal Info");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ProfileName = dataSnapshot.child("name").getValue().toString();
                mPhone = dataSnapshot.child("number").getValue().toString();
                mEmail = dataSnapshot.child("email").getValue().toString();
                mDistrict = dataSnapshot.child("district").getValue().toString();
                mTotalLand = dataSnapshot.child("Total land").getValue().toString();
                mCultiLand = dataSnapshot.child("Cultivated Land").getValue().toString();
                mName.setText(ProfileName);
                Phone.setText(mPhone);
                Email.setText(mEmail);
                District.setText(mDistrict);
                TotalLand.setText(mTotalLand);
                CultiLand.setText(mCultiLand);
                shimmerFrameLayout.stopShimmerAnimation();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent mainIntent = new Intent(ProfileActivity.this, StartActivity.class);

                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(mainIntent);

                finish();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if(ProfileName==null) {
            shimmerFrameLayout.startShimmerAnimation();
        }
        else {
            shimmerFrameLayout.stopShimmerAnimation();
        }
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }
}

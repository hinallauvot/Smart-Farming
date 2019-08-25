package com.example.hinal.smartfarming;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActPlanActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView mMorePlans;
    List<ModelActivatedPlans> activePlans;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    DatabaseReference mDatabase;
    String data = "";
    PlanRecyclerView recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_plan);
        mToolbar = findViewById(R.id.plan_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Plans");

        activePlans=new ArrayList<>();




        recyclerView =  findViewById(R.id.my_recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);




        //RecyclerView layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //RecyclerView adapater
        recyclerViewAdapter = new PlanRecyclerView(ActPlanActivity.this,activePlans);
        recyclerView.setAdapter(recyclerViewAdapter);


        mMorePlans = findViewById(R.id.morePlans);
        mMorePlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(ActPlanActivity.this, BrandsFilterActivity.class);
                startActivity(mainIntent);
            }
        });


        //swipe refresh layout

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                    }
                },4000);


            }

        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Activated Plans");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ModelActivatedPlans m = new ModelActivatedPlans();
                     m = dataSnapshot1.getValue(ModelActivatedPlans.class);
                    activePlans.add(m);
                    recyclerViewAdapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }














}

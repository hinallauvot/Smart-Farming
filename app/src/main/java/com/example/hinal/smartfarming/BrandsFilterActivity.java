package com.example.hinal.smartfarming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BrandsFilterActivity extends AppCompatActivity{
    private RecyclerView brandRecyclerView;
    private Toolbar mToolbar;
    private Button mButton;
    SimpleDateFormat sdf;
    String ActivationDate;
    String ExpiryDate;
    Calendar calendar;
    DatabaseReference databaseReference;
    private List<ModelActivatedPlans> ActivatedPlansData = new ArrayList<ModelActivatedPlans>();
    List<ModelPlan> modelList = new ArrayList<ModelPlan>();



    public BrandsFilterActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Chooose your Plan");

        brandRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mButton = findViewById(R.id.btnShow);

        //RecyclerView layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        brandRecyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(brandRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        brandRecyclerView.addItemDecoration(dividerItemDecoration);

        //data


        modelList.add(new ModelPlan(R.drawable.pomegranate,"Pomegranate Plan", "7 months(210 days) of consulting for pomegranate related queries only,without any limits on number of queries!", "Rs: 500",false,210));
        modelList.add(new ModelPlan(R.drawable.grapes,"Grapes Plan","4 months(120 days) of consulting for grapes related queries only,without any limits on number of queries", "Rs: 1000",false,120));
        modelList.add(new ModelPlan(R.drawable.tomato,"Tomato Plan", "3 months(90 days) of consulting for tomato related queries only,without any limits on number of queries", "Rs: 500",false,90));
        modelList.add(new ModelPlan(R.drawable.chilli,"Chili Plan", "3 months(90 days) of consulting for chili related queries only,without any limits on number of queries", "Rs: 400",false,90));
        modelList.add(new ModelPlan(R.drawable.brinjal,"Brinjal Plan", "4 months(120 days) of consulting for brinjal related queries only,without any limits on number of queries", "Rs: 500",false,120));
        modelList.add(new ModelPlan(R.drawable.papaya,"Papaya Plan", "12 months(365 days) of consulting for papaya related queries only,without any limits on number of queries", "Rs: 1000",false,365));
        modelList.add(new ModelPlan(R.drawable.bottlegourd,"Bottle Gourd Plan", "6 months(180 days) of consulting for bottle gourd related queries only,without any limits on number of queries", "Rs: 500",false,180));
        modelList.add(new ModelPlan(R.drawable.ladyfinger,"Ladyfinger Plan", "4 months(120 days) of consulting for ladyfinger related queries only,without any limits on number of queries", "Rs: 400",false,120));
        modelList.add(new ModelPlan(R.drawable.capcicum,"Capcicum Plan", "5 months(150 days) of consulting for capcicum related queries only,without any limits on number of queries", "Rs: 500",false,150));
        modelList.add(new ModelPlan(R.drawable.bittergourdre,"Bitter gourd Plan", "5 months(150 days) of consulting for bitter gourd related queries only,without any limits on number of queries", "Rs: 500",false,150));
        modelList.add(new ModelPlan(R.drawable.rosere,"Rose Plan", "9 months(270 days) of consulting for pomegranate related queries only,without any limits on number of queries", "Rs: 900",false,270));
        modelList.add(new ModelPlan(R.drawable.marigold,"Marigold Plan", "5 months(150 days) of consulting for pomegranate related queries only,without any limits on number of queries", "Rs: 500",false,500));
        modelList.add(new ModelPlan(R.drawable.general,"General Plan", "1 months(30 days) of consulting for general queries excluding queries related to aforementioned plans without any limits on number of queries", "Rs: 200",false,30));


        //RecyclerView adapater
        final ProductFilterRecyclerViewAdapter recyclerViewAdapter = new ProductFilterRecyclerViewAdapter(modelList,this);
        brandRecyclerView.setAdapter(recyclerViewAdapter);








        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";

                List<ModelPlan> stList = ((ProductFilterRecyclerViewAdapter) recyclerViewAdapter).getFilterList();



                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users");







                for (int i = 0; i < stList.size(); i++) {
                    ModelPlan singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {
                        calendar = Calendar.getInstance();
                        sdf = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.setTime(new Date());
                        ActivationDate = sdf.format(calendar.getTime());
                        calendar.add(Calendar.DATE, singleStudent.getDays());
                        ExpiryDate = sdf.format(calendar.getTime());

                        ModelActivatedPlans activatedPlansData =  new ModelActivatedPlans(singleStudent.getPlanName(), ActivationDate, ExpiryDate);
                        ActivatedPlansData.add(new ModelActivatedPlans(singleStudent.getPlanName(), ActivationDate, ExpiryDate));
                        String mKey =UUID.randomUUID().toString();
                        databaseReference.child(uid).child("Activated Plans").child(mKey).setValue(activatedPlansData);



                    }
                }


                Intent mainIntent = new Intent(BrandsFilterActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });
    }


}

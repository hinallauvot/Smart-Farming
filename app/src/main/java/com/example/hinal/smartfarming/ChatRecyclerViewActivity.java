package com.example.hinal.smartfarming;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatRecyclerViewActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String uid;
    String UserName;
    RecyclerView recyclerView;
    DatabaseReference firebaseDatabase;
    DatabaseReference firebaseDatabase1;
    DatabaseReference firebaseDatabase2;
    DatabaseReference firebaseDatabase3;
    DatabaseReference mDatabase;
    private static String adminUserID="1BEKpz7tWvfgDHYw7z7ZIMe0hZw1";
    FloatingActionButton call, message;
    ArrayList<ModelUserName> FriendsUserName = new ArrayList<>();;
    ChatRecyclerAdapter chatRecyclerAdapter;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_recycler_view);


        mToolbar = findViewById(R.id.queries_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Queries");


        FriendsUserName = new ArrayList<>();

        call = (FloatingActionButton) findViewById(R.id.subFloatingMenu2);
        message = (FloatingActionButton) findViewById(R.id.subFloatingMenu3);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerView =  findViewById(R.id.list);






        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //RecyclerView adapater
        chatRecyclerAdapter = new ChatRecyclerAdapter(ChatRecyclerViewActivity.this,FriendsUserName);
        recyclerView.setAdapter(chatRecyclerAdapter);



        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    if(!uid.equals(adminUserID)){

                        firebaseDatabase1 = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Personal Info");
                        firebaseDatabase1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserName = dataSnapshot.child("name").getValue().toString();


                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("name",UserName);
                                firebaseDatabase2 = FirebaseDatabase.getInstance().getReference("Users");
                                firebaseDatabase2.child(adminUserID).child("friends").child(uid).setValue(userMap);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                            HashMap<String, String> consMap = new HashMap<>();
                            consMap.put("name", "Ask Consultant");


                            firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
                            firebaseDatabase.child(uid).child("friends").child(adminUserID).setValue(consMap);








                    }
                    else{

                        Toast.makeText(ChatRecyclerViewActivity.this, "Consultant cant ask queries.", Toast.LENGTH_SHORT).show();



                    }

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8830820387"));
                if (ContextCompat.checkSelfPermission(ChatRecyclerViewActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestCallPermission();


                }else{
                    startActivity(callIntent);

                }


            }

            private void requestCallPermission()
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ChatRecyclerViewActivity.this,Manifest.permission.INTERNET)){

                }else{
                    ActivityCompat.requestPermissions(ChatRecyclerViewActivity.this,new String[]{Manifest.permission.CALL_PHONE},9);
                }
            }
        });


        firebaseDatabase3 = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("friends");

        firebaseDatabase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String m = new String();
                    String n = new String();
                    m = dataSnapshot1.child("name").getValue().toString();
                    n= dataSnapshot1.getKey().toString();

                    FriendsUserName.add(new ModelUserName(m,n));

                    chatRecyclerAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ChatRecyclerViewActivity.this, "Database error.", Toast.LENGTH_SHORT).show();

            }
        });














    }
}

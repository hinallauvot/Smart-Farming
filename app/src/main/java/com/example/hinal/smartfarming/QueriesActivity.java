package com.example.hinal.smartfarming;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QueriesActivity extends AppCompatActivity {
    Toolbar mToolbar;
    EditText messageBox;
    ImageView sendBtn;
    ImageView imageSendBtn;
    ModelChat queryMessage;
    String uid;
    RequestOptions options;
    ImageView imageViewMessage;
    QueryRecyclerAdapter queryRecyclerAdapter;
    private RecyclerView mMessagesList;
    private LinearLayoutManager mLinearLayout;
    private SwipeRefreshLayout mRefreshLayout;
    ArrayList<ModelChat> queryMessagesList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int mCurrentPage = 1;
    private static final int GALLERY_PICK = 1;
    Intent intent;
    String UserKey;
    private FirebaseStorage mImageStorage;

    private int itemPos = 0;



    private String mLastKey = "";

    private String mPrevKey = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        intent = getIntent();
        String UserName=intent.getStringExtra("user_name");
        UserKey= intent.getStringExtra("user_key");
        mToolbar = findViewById(R.id.queries_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(UserName);
        queryMessagesList = new ArrayList<ModelChat>();
        messageBox = findViewById(R.id.messageArea);
        sendBtn = findViewById(R.id.sendButton);
        imageSendBtn = findViewById(R.id.imgButton);
        mMessagesList = (RecyclerView) findViewById(R.id.messages_list);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe_layout);


        imageViewMessage = findViewById(R.id.imageMessageRight);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mImageStorage = FirebaseStorage.getInstance();



        mLinearLayout = new LinearLayoutManager(this);



        mMessagesList.setHasFixedSize(true);

        mMessagesList.setLayoutManager(mLinearLayout);


        queryRecyclerAdapter = new QueryRecyclerAdapter(this,queryMessagesList);
        mMessagesList.setAdapter(queryRecyclerAdapter);

        loadMessages();








            sendBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    String message = messageBox.getText().toString();

                    if(!TextUtils.isEmpty(message)) {

                        InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                        inputManager.restartInput(messageBox);


                        Map messageMap = new HashMap();

                        messageMap.put("message",message );
                        messageMap.put("type", "text");

                        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
                        Date date = new Date();
                        String DateTime =formatter.format(date);
                        queryMessage = new ModelChat(uid,message,"text");
                        mDatabase = FirebaseDatabase.getInstance().getReference("Messages");
                        mDatabase1 = FirebaseDatabase.getInstance().getReference("Messages");
                        mDatabase.child(uid).child(UserKey).child(uid+DateTime).setValue(messageMap);
                        mDatabase1.child(UserKey).child(uid).child(uid+DateTime).setValue(messageMap);
                        messageBox.setText("");

                    }
                    else
                    {
                        Toast.makeText(QueriesActivity.this, "No query typed", Toast.LENGTH_SHORT).show();
                    }


                }
            });


            imageSendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
                }
            });











        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {



                mCurrentPage++;



                itemPos = 0;



                loadMoreMessages();





            }

        });




    }

    private void loadMessages() {

        mDatabase = FirebaseDatabase.getInstance().getReference("Messages").child(uid).child(UserKey);



        Query messageQuery = mDatabase.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);





        messageQuery.addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String messageKey = dataSnapshot.getKey();
                String message = dataSnapshot.child("message").getValue().toString();
                String type = dataSnapshot.child("type").getValue().toString();
                String user = new String();




                ModelChat modelChat =new ModelChat(messageKey, message,type);



                itemPos++;



                if(itemPos == 1){






                    mLastKey = messageKey;

                    mPrevKey = messageKey;



                }



                queryMessagesList.add(modelChat);

                queryRecyclerAdapter.notifyDataSetChanged();



                mMessagesList.scrollToPosition(queryMessagesList.size() - 1);



                mRefreshLayout.setRefreshing(false);



            }



            @Override

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }



            @Override

            public void onChildRemoved(DataSnapshot dataSnapshot) {



            }



            @Override

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {



            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });
    }



    private void loadMoreMessages() {



        mDatabase = FirebaseDatabase.getInstance().getReference("Messages").child(uid).child(UserKey);



        Query messageQuery = mDatabase.orderByKey().endAt(mLastKey).limitToLast(10);



        messageQuery.addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {





                String message = dataSnapshot.child("message").getValue().toString();
                String type = dataSnapshot.child("type").getValue().toString();
                String user = new String();
                String messageKey = dataSnapshot.getKey();



                ModelChat modelChat =new ModelChat(messageKey, message,type);



                if(!mPrevKey.equals(messageKey)){



                    queryMessagesList.add(itemPos++, modelChat);



                } else {



                    mPrevKey = mLastKey;



                }





                if(itemPos == 1) {



                    mLastKey = messageKey;



                }





                Log.d("TOTALKEYS", "Last Key : " + mLastKey + " | Prev Key : " + mPrevKey + " | Message Key : " + messageKey);



                queryRecyclerAdapter.notifyDataSetChanged();



                mRefreshLayout.setRefreshing(false);



                mLinearLayout.scrollToPositionWithOffset(10, 0);



            }



            @Override

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }



            @Override

            public void onChildRemoved(DataSnapshot dataSnapshot) {



            }



            @Override

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {



            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });












    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).diskCacheStrategy(DiskCacheStrategy.ALL);


        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {


            Uri imageUri = data.getData();
            Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show();
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
            Date date = new Date();
            final String DateTime = formatter.format(date);


            final String push_id = uid + DateTime;


            final StorageReference filepath = mImageStorage.getReference().child(push_id+"."+getFileExtension(imageUri));

            UploadTask uploadTask = filepath.putFile(imageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(QueriesActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    Toast.makeText(QueriesActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                }
            });
            Map messageMap = new HashMap();

            messageMap.put("message", "https://firebasestorage.googleapis.com/v0/b/smartfarming-a41a3.appspot.com/o/1BEKpz7tWvfgDHYw7z7ZIMe0hZw118022019145502.jpg?alt=media&token=260b5e58-2277-41a5-b542-9f0105a24e1b");
            messageMap.put("type", "image");
            queryMessage = new ModelChat(uid, "https://firebasestorage.googleapis.com/v0/b/smartfarming-a41a3.appspot.com/o/1BEKpz7tWvfgDHYw7z7ZIMe0hZw118022019145502.jpg?alt=media&token=260b5e58-2277-41a5-b542-9f0105a24e1b", "image");
            mDatabase = FirebaseDatabase.getInstance().getReference("Messages");
            mDatabase1 = FirebaseDatabase.getInstance().getReference("Messages");
            mDatabase.child(uid).child(UserKey).child(uid + DateTime).setValue(messageMap);
            mDatabase1.child(UserKey).child(uid).child(uid + DateTime).setValue(messageMap);

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            storageReference.child(push_id+"."+getFileExtension(imageUri)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Map messageMap = new HashMap();

                    Toast.makeText(QueriesActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();


                }
            });


            /*StorageTask  mUploadTask = filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloadUri= taskSnapshot.getStorage().getDownloadUrl();
                            downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map messageMap = new HashMap();
                                    messageMap.put("message", uri);
                                    messageMap.put("type", "image");
                                    queryMessage = new ModelChat(uid, uri.toString(), "image");
                                    mDatabase = FirebaseDatabase.getInstance().getReference("Messages");
                                    mDatabase1 = FirebaseDatabase.getInstance().getReference("Messages");
                                    mDatabase.child(uid).child(UserKey).child(uid + DateTime).setValue(messageMap);
                                    mDatabase1.child(UserKey).child(uid).child(uid + DateTime).setValue(messageMap);
                                }
                            });
                            Toast.makeText(QueriesActivity.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });*/








                        }

                    }










    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));






    }

}

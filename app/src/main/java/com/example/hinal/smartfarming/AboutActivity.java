package com.example.hinal.smartfarming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView ConsultName;
    Button Share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ConsultName = findViewById(R.id.profile_consultant);
        Share = findViewById(R.id.share);
        ConsultName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(AboutActivity.this, ConsultantActivity.class);
                startActivity(mainIntent);

            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShareIt = new Intent(Intent.ACTION_SEND);
                ShareIt.setType("text/plain");
                String shareBody = "Smart Farming";
                String shareSub = "https://drive.google.com/open?id=1T2EmHTTuvg5ahc9RtCmY2rJikuaVNGFC";
                ShareIt.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                ShareIt.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(ShareIt,"Share Using..."));
            }
        });

    }
}

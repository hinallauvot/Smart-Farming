package com.example.hinal.smartfarming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends Activity {
    Button already_acc;
    Button create_acc;
    TextView welcome;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        already_acc = findViewById(R.id.start_login_btn);
        create_acc = findViewById(R.id.start_reg_btn);
        logo = findViewById(R.id.app_logo);
        welcome = findViewById(R.id.textView);
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        logo.startAnimation(anim);
        welcome.startAnimation(anim);
        logo.setVisibility(View.VISIBLE);
        welcome.setVisibility(View.VISIBLE);
        already_acc.setVisibility(View.VISIBLE);
        create_acc.setVisibility(View.VISIBLE);
        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}

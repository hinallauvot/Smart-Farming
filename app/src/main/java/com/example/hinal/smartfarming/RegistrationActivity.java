package com.example.hinal.smartfarming;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private Toolbar mToolbar;
    AwesomeValidation awesomeValidation;
    private DatabaseReference mDatabase;
    //ProgressDialog
    private ProgressDialog mRegProgress;
    //Firebase Auth
    private FirebaseAuth mAuth;

    //feilds
    private TextInputLayout mName;
    private TextInputLayout mNumber;
    private TextInputLayout mEmail;
    private TextInputLayout mDistrict;
    private TextInputLayout mPassword;
    private TextInputLayout mConfirmPass;
    private TextInputLayout mTotalLand;
    private TextInputLayout mCultLand;
    private Button mCreateBtn;
    private ImageView iv;
    private Spinner total,culti;

    String display_name ;
    String Number;
    String Email;
    String District;
    String ToatalLand;
    String CultiLand;
    String Password ;
    String ConfirmPass;
    private TextInputEditText name;
    private TextInputEditText number;
    private TextInputEditText email;
    private TextInputEditText district;
    private TextInputEditText password;
    private TextInputEditText conPass;
    private String UnitTotal;
    private String UnitCulti;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mToolbar = findViewById(R.id.reg_toolbar);
        awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        awesomeValidation.setColor(R.color.tablayoutColor);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create an account");










        mRegProgress = new ProgressDialog(this);


        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.index_email);
        mNumber = findViewById(R.id.phone_no);
        mEmail = findViewById(R.id.id_email);
        mDistrict = findViewById(R.id.id_district);
        mTotalLand = findViewById(R.id.totalland);
        mCultLand = findViewById(R.id.cultiLand);
        mPassword = findViewById(R.id.input_password);
        mConfirmPass = findViewById(R.id.confirm_password);
        mCreateBtn = findViewById(R.id.btn_login);
        name = findViewById(R.id.name);
        number = findViewById(R.id.ed_phone);
        email = findViewById(R.id.ed_email);
        district = findViewById(R.id.ed_district);
        password = findViewById(R.id.ed_pass);
        conPass = findViewById(R.id.ed_confirmPass);

        total = findViewById(R.id.spinnner_total);
        culti = findViewById(R.id.spinner_cult);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{6,}";



        awesomeValidation.addValidation(RegistrationActivity.this, R.id.index_email, "[a-zA-Z\\s]+", R.string.nameErr);
        awesomeValidation.addValidation(RegistrationActivity.this, R.id.phone_no, RegexTemplate.TELEPHONE, R.string.phoneErr);
        awesomeValidation.addValidation(RegistrationActivity.this,R.id.id_email,Patterns.EMAIL_ADDRESS,R.string.emailErr);
        awesomeValidation.addValidation(RegistrationActivity.this,R.id.id_district,"[a-zA-Z\\s]+",R.string.districtErr);
        awesomeValidation.addValidation(RegistrationActivity.this,R.id.input_password,regexPassword,R.string.passErr);
        awesomeValidation.addValidation(RegistrationActivity.this,R.id.confirm_password,R.id.input_password,R.string.conPass);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {


                 display_name = mName.getEditText().getText().toString();
                 Number = mNumber.getEditText().getText().toString();
                 Email = mEmail.getEditText().getText().toString();
                 District = mDistrict.getEditText().getText().toString();
                 ToatalLand = mTotalLand.getEditText().getText().toString();
                 CultiLand = mCultLand.getEditText().getText().toString();
                 Password = mPassword.getEditText().getText().toString();
                 ConfirmPass = mConfirmPass.getEditText().getText().toString();
                 UnitTotal = total.getSelectedItem().toString();
                 UnitCulti = culti.getSelectedItem().toString();









                if(awesomeValidation.validate()){

                    if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(Email) || !TextUtils.isEmpty(Password) || !TextUtils.isEmpty(Number) || !TextUtils.isEmpty(ConfirmPass) || !TextUtils.isEmpty(District)) {


                        mRegProgress.setTitle("Registering User");

                        mRegProgress.setMessage("Please wait while we create your account !");

                        mRegProgress.setCanceledOnTouchOutside(false);

                        mRegProgress.show();


                        register_user(display_name, Email, Password, ConfirmPass, District, Number);


                    }




                }




            }

        });



    }

    private void register_user(final String display_name, final String email, String password, String confirmPass, String district, String number) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()) {

                    String TotalLandU = ToatalLand +" "+UnitTotal;
                    String CultiLnadU = CultiLand + " "+UnitCulti;


                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name",display_name);
                    userMap.put("number",Number);
                    userMap.put("email",Email);
                    userMap.put("district",District);
                    userMap.put("Total land",TotalLandU);
                    userMap.put("Cultivated Land",CultiLnadU);
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                    mDatabase.child(uid).child("Personal Info").setValue(userMap);

                        mRegProgress.dismiss();


                        Intent mainIntent = new Intent(RegistrationActivity.this, BrandsFilterActivity.class);

                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(mainIntent);

                        finish();




                }






                else {



                    mRegProgress.hide();

                    Toast.makeText(RegistrationActivity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();


                }



            }

        });



    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
















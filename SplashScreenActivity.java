package com.example.ams.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ams.database.AttendanceManagementSystemDatabase;
import com.example.ams.loginpage.loginpage;
import com.example.ams.registration.RegistrationActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AttendanceManagementSystemDatabase db = new AttendanceManagementSystemDatabase(getApplicationContext());
        //boolean is = db.isAnyUser();
        //Log.i("User", String.valueOf(is));
        if(db.isAnyUser()){
            startActivity(new Intent(SplashScreenActivity.this, loginpage.class));
            finish();
        }
        else {
            startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
            finish();
        }
    }
}
package com.example.ams.loginpage;


//import android.hardware.biometrics.BiometricPrompt;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricPrompt.PromptInfo;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import com.example.ams.R;
import com.example.ams.database.AttendanceManagementSystemDatabase;
import com.example.ams.main.MainActivity;

//import com.example.atry.demoo;

//import org.chromium.base.Callback;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class loginpage extends AppCompatActivity {

    Button loginbutton;
    Button fingerbutton;
    EditText loginemail,loginpass;
    AttendanceManagementSystemDatabase dbb;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private  BiometricPrompt.PromptInfo promptInfo;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);



        loginbutton=(Button) findViewById(R.id.loginbutton);
        loginemail=(EditText)findViewById(R.id.loginemail);
        loginpass=(EditText)findViewById(R.id.loginpass);

        //    FragmentActivity activity = this;
        dbb = new AttendanceManagementSystemDatabase(loginpage.this);


        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(loginpage.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_LONG).show();
                finish();
                Intent intt=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intt);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for Attendance Management System")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account userName and password")
                .build();
        biometricPrompt.authenticate(promptInfo);






     /*  executor = Executors.newSingleThreadExecutor();
      biometricPrompt=new BiometricPrompt(activity, executor,
           BiometricPrompt.AuthenticationCallback(){


       })*/
        //   BiometricPrompt.AuthenticationCallback authenticationCallback= get


    /*    Executor executor = Executors.newSingleThreadExecutor();

        FragmentActivity activity = this;

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //TODO: Called when a biometric is recognized.
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //TODO: Called when a biometric is valid but not recognized.
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("fingerprint Authentication")
                .setSubtitle("Set the subtitle to display.")
                .setDescription("Set the description to display")
                .setNegativeButtonText("Negative Button")
                .build();

        findViewById(R.id.authenticateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

*/


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginemail.getText().toString();
                String password = loginpass.getText().toString();
                //call method to cehck user is valid or not
                boolean b1 = dbb.isValidEmailAndPassword(email, password);
                if (b1 == false) {
                    Toast.makeText(loginpage.this, "Enter valid Information", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(loginpage.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    Intent intt=new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(intt);

                }


            }



        });


    }

    public void onBackPressed(){finish();}
}
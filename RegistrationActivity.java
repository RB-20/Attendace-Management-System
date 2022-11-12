package com.example.ams.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ams.R;
import com.example.ams.database.AttendanceManagementSystemDatabase;
import com.example.ams.loginpage.loginpage;
import com.example.ams.main.MainActivity;

public class RegistrationActivity<getString> extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText userEmail;
    private EditText userPassword;
    private EditText userConfirmPassword;
    private Spinner yr_spinner;
    private Spinner div_spinner;
    //private Spinner sub_spinner;
    private String userEmail_txt;
    private String userPassword_txt;
    private String userConfirmPassword_txt;
    private String text_yr;
    private String text_div;
    private String text_sub;

    AttendanceManagementSystemDatabase ams_Db;
    Button registrationButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ams_Db = new AttendanceManagementSystemDatabase(this);
        boolean is = ams_Db.isAnyUser();
        setContentView(R.layout.activity_registration);
        if(is == true){
            finish();
            Intent intent = new Intent(this,loginpage.class);
            startActivity(intent);
        }
        userEmail = findViewById(R.id.userEmail);
        registrationButton = findViewById(R.id.registrationButton);
        userPassword = findViewById(R.id.userPassword);
        userConfirmPassword = findViewById(R.id.userConfirmPassword);
        yr_spinner = findViewById(R.id.year_spinner);
        div_spinner = findViewById(R.id.division_spinner);
        //sub_spinner = findViewById(R.id.subject_spinner);


        //Array adapter for year spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.year_semester, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yr_spinner.setAdapter(adapter1);
        yr_spinner.setOnItemSelectedListener(this);

        //Array adapter for division spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.division, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        div_spinner.setAdapter(adapter2);
        //div_spinner.setOnItemSelectedListener(this);

        ;


        //TextWatchers for all EditTexts
        userEmail.addTextChangedListener(registrationTextWatcher);
        userPassword.addTextChangedListener(registrationTextWatcher);
        userConfirmPassword.addTextChangedListener(registrationTextWatcher);




        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner yearSpinner, divisionSpinner, subjectSpinner;
                yearSpinner = (Spinner)findViewById(R.id.year_spinner);
                divisionSpinner = (Spinner)findViewById(R.id.division_spinner);
                subjectSpinner = (Spinner)findViewById(R.id.subject_spinner);

                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                String year = (String)yearSpinner.getSelectedItem();
                String division = (String)divisionSpinner.getSelectedItem();
                String subject = (String)subjectSpinner.getSelectedItem();

                ams_Db.createNewUser(email, password);
                ams_Db.createNewLectureSubject(subject, year, division);
                ams_Db.createNewPracticalSubject(subject, year, division);

                finish();
                openLogin();
            }
        });

    }

    public void openLogin(){
        Intent intent = new Intent(getApplicationContext(), loginpage.class);
        startActivity(intent);
    }

    //method to validate email
    private boolean IsEmailValid(EditText userEmail) {
        userEmail_txt = userEmail.getText().toString();
        if (!userEmail_txt.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userEmail_txt).matches()) {
            return true;
        } else {
            return false;
        }
    }

    //method to validate password
    private boolean IsPasswordValid(EditText userPassword) {
        userPassword_txt = userPassword.getText().toString();
        if (userPassword_txt.length() >= 8) {
            return true;
        } else {

            return false;
        }
    }

    //method to confirm password
    private boolean IsConfirmPasswordValid(EditText userConfirmPassword) {
        userPassword_txt = userPassword.getText().toString();
        userConfirmPassword_txt = userConfirmPassword.getText().toString();
        if (userPassword_txt.equals(userConfirmPassword_txt)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text_yr = yr_spinner.getItemAtPosition(position).toString();
        Log.i("Year: ", text_yr);


        switch (position){
            case 0:
                updateSubjectList(getResources().getStringArray(R.array.first_sem));
                break;
            case 1:
                updateSubjectList(getResources().getStringArray(R.array.second_sem));
                break;
            case 2:
                updateSubjectList(getResources().getStringArray(R.array.third_sem));
                break;
            case 3:
                updateSubjectList(getResources().getStringArray(R.array.fourth_sem));
                break;
            case 4:
                updateSubjectList(getResources().getStringArray(R.array.fifth_sem));
                break;
            case 5:
                updateSubjectList(getResources().getStringArray(R.array.sixth_sem));
                break;
            case 6:
                updateSubjectList(getResources().getStringArray(R.array.seventh_sem));
                break;
            case 7:
                updateSubjectList(getResources().getStringArray(R.array.eighth_sem));
                break;
        }


        text_div = (String)div_spinner.getSelectedItem();
        //text_sub = sub_spinner.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateSubjectList(String[] items) {
        Spinner sub_spinner = findViewById(R.id.subject_spinner);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        sub_spinner.setAdapter(adapter3);
        text_sub = (String)sub_spinner.getSelectedItem();
        Log.i("Subject: ", text_sub);
    }

    private TextWatcher registrationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!IsEmailValid(userEmail))
                userEmail.setError("Enter a valid address");
            if (!IsPasswordValid(userPassword))
                userPassword.setError("Password should have more than 8 characters");
            if (!IsConfirmPasswordValid(userConfirmPassword))
                userConfirmPassword.setError("Both passwords should match");
            registrationButton.setEnabled(IsEmailValid(userEmail) && IsPasswordValid(userPassword) && IsConfirmPasswordValid(userConfirmPassword));

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void onBackPressed(){
        finish();
    }

}

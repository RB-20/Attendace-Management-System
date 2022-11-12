package com.example.ams.manage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ams.R;
import com.example.ams.database.AttendanceManagementSystemDatabase;
import com.example.ams.main.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NewAttendance extends AppCompatActivity{
    public AttendanceManagementSystemDatabase attendanceManagementSystemDatabase;

    Button dateButton;
    Calendar calendar;
    int year,month,day;
    String date = "";

    ArrayList<Integer> absentList = new ArrayList<>();
    TextView absentTextView;
    EditText absentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_attendance);

        //get access of widgets
        absentTextView = (TextView) findViewById(R.id.absentListTextView);
        absentEditText = (EditText) findViewById(R.id.absentEditText);
        dateButton = (Button) findViewById(R.id.dateButton);

        //set present date
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = day+"/"+month+"/"+year;
        dateButton.setText(date.toString());

        attendanceManagementSystemDatabase = new AttendanceManagementSystemDatabase(this);
    }

    //TODO: TO SET THE DATE FOR THE ATTENDANCE
    public void setDate(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth+"/"+(month+1)+"/"+year;
                dateButton.setText(date.toString());
            }
        },year,month,day);
        datePickerDialog.show();
    }

    //TODO: TO ADD THE ROLL NUMBER IN ABSENT LIST
    public void addRoll(View view) {
        try {
            int roll = Integer.parseInt(absentEditText.getText().toString());
            boolean rollNoInList = false;
            if (roll <= 0) {
                Toast.makeText(this, "Invalid Roll no.!", Toast.LENGTH_SHORT).show();
            }
            else if(!(roll >= attendanceManagementSystemDatabase.rollFrom && roll <= attendanceManagementSystemDatabase.rollTo)){
                Toast.makeText(this, roll+" is out of Batch", Toast.LENGTH_SHORT).show();
            }
            else {
                for(int x = 0; x < absentList.size(); x++){
                    if(roll == absentList.get(x)){
                        rollNoInList = true;
                        break;
                    }
                }
                if(rollNoInList) {
                    Toast.makeText(this, "Roll no. "+ roll +" is already added", Toast.LENGTH_SHORT).show();
                }
                else {
                    absentList.add(roll);
                    Collections.sort(absentList);
                    absentTextView.setText(absentList.toString());
                }
                absentEditText.setText("");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please enter the roll no.", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: TO REMOVE THE ROLL NUMBER FROM ABSENT LIST
    public void removeRoll(View view) {
        try {
            int roll = Integer.parseInt(absentEditText.getText().toString());
            int index = 0;
            boolean rollNoInList = false;
            if (absentList.size() == 0) {
                Toast.makeText(this, "Absent list is empty", Toast.LENGTH_SHORT).show();
                rollNoInList = false;
            }
            else {
                for (int x = 0; x < absentList.size(); x++) {
                    if (roll == absentList.get(x)) {
                        index = x;
                        rollNoInList = true;
                        break;
                    }
                }
                if (rollNoInList) {
                    absentList.remove(index);
                    absentTextView.setText(absentList.toString());
                }
                else {
                    Toast.makeText(this, "Roll no. " + roll + " is not in absent list", Toast.LENGTH_SHORT).show();
                }
            }
            absentEditText.setText("");
        }catch (Exception e){}

    }

    //TODO: TO SAVE THE ATTENDANCE
    public void saveAttendance(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to save the attendance");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String d = date.toString();
                String a = absentList.toString();
                a = a.substring(1,a.length()-1);
                attendanceManagementSystemDatabase.addNewAttendance(d,a);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                if(absentList.size() == 0)
                    Toast.makeText(getApplicationContext(),"All students are present",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle(attendanceManagementSystemDatabase.setSubject);
        alertDialog.show();
    }

}

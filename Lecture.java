package com.example.ams.tabs;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.ams.R;
import com.example.ams.database.AttendanceManagementSystemDatabase;
import com.example.ams.manage.NewAttendance;
import com.example.ams.manage.ViewReport;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lecture extends Fragment {
    public AttendanceManagementSystemDatabase attendanceManagementSystemDatabase;
    public String action = "";
    public ListView listView;
    public String[][] lectureData = new String[][]{};
    public String[] data = new String[]{};
    public String[] selection = new String[]{};

    public Lecture(String a) {
        this.action = a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root =  inflater.inflate(R.layout.fragment_lecture, container, false);
        listView = (ListView)root.findViewById(R.id.lectureListView);
        attendanceManagementSystemDatabase = new AttendanceManagementSystemDatabase(getContext());

        lectureData = attendanceManagementSystemDatabase.getLectureList();
        data = lectureData[0];
        selection = new String[]{
                "\nSubject: "+data[0] +
                        "\nYear:  "+data[1]+
                        "\nDivision:   "+data[2]+"\n"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,android.R.id.text1,selection);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = arrayAdapter.getItem(position);
                if(str == selection[0]) {
                    attendanceManagementSystemDatabase.setSubject = data[0];
                    attendanceManagementSystemDatabase.setYear = data[1];
                    attendanceManagementSystemDatabase.setDivision = data[2];
                    attendanceManagementSystemDatabase.setCategory = "Lecture";
                    attendanceManagementSystemDatabase.rollFrom = 1;
                    attendanceManagementSystemDatabase.rollTo = 90;

                    if(action.equals("Attendance")) {
                        Intent intent = new Intent(getContext(), NewAttendance.class);
                        startActivity(intent);
                    }
                    else if(action.equals("Report")) {
                        Intent intent = new Intent(getContext(), ViewReport.class);
                        startActivity(intent);
                    }

                }
            }
        });

        return root;
    }


}
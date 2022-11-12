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

import com.example.ams.R;
import com.example.ams.database.AttendanceManagementSystemDatabase;
import com.example.ams.manage.NewAttendance;
import com.example.ams.manage.ViewReport;

/**
 * A simple {@link Fragment} subclass.
 */
public class Practical extends Fragment {
    public AttendanceManagementSystemDatabase attendanceManagementSystemDatabase;
    public String action = "";
    public ListView listView;
    public String[][] practicalData = new String[][]{};
    public String[] data1 = new String[]{};
    public String[] data2 = new String[]{};
    public String[] data3 = new String[]{};
    public String[] data4 = new String[]{};
    public String[] data5 = new String[]{};
    public String[] selection = new String[]{};

    public Practical(String a) {
        this.action = a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_practical, container, false);
        listView = (ListView)root.findViewById(R.id.practicalListView);
        attendanceManagementSystemDatabase = new AttendanceManagementSystemDatabase(getContext());

        practicalData = attendanceManagementSystemDatabase.getPracticalList();
        data1 = practicalData[0];
        data2 = practicalData[1];
        data3 = practicalData[2];
        data4 = practicalData[3];
        data5 = practicalData[4];
        selection = new String[]{
                "\nSubject: "+data1[0] + "\nYear:  "+data1[1]+ "\nDivision:   "+data1[2]+ "\nBatch:   "+data1[3] + "\n",
                "\nSubject: "+data2[0] + "\nYear:  "+data2[1]+ "\nDivision:   "+data2[2]+ "\nBatch:   "+data2[3] + "\n",
                "\nSubject: "+data3[0] + "\nYear:  "+data3[1]+ "\nDivision:   "+data3[2]+ "\nBatch:   "+data3[3] + "\n",
                "\nSubject: "+data4[0] + "\nYear:  "+data4[1]+ "\nDivision:   "+data4[2]+ "\nBatch:   "+data4[3] + "\n",
                "\nSubject: "+data5[0] + "\nYear:  "+data5[1]+ "\nDivision:   "+data5[2]+ "\nBatch:   "+data5[3] + "\n"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,android.R.id.text1,selection);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = arrayAdapter.getItem(position);
                if(str == selection[0]) {
                    attendanceManagementSystemDatabase.setSubject = data1[0];
                    attendanceManagementSystemDatabase.setYear = data1[1];
                    attendanceManagementSystemDatabase.setDivision = data1[2];
                    attendanceManagementSystemDatabase.setBatch = data1[3];
                    attendanceManagementSystemDatabase.setCategory = "Practical";
                    attendanceManagementSystemDatabase.rollFrom = 1;
                    attendanceManagementSystemDatabase.rollTo = 18;
                }
                if(str == selection[1]){
                    attendanceManagementSystemDatabase.setSubject = data2[0];
                    attendanceManagementSystemDatabase.setYear = data2[1];
                    attendanceManagementSystemDatabase.setDivision = data2[2];
                    attendanceManagementSystemDatabase.setBatch = data2[3];
                    attendanceManagementSystemDatabase.setCategory = "Practical";
                    attendanceManagementSystemDatabase.rollFrom = 19;
                    attendanceManagementSystemDatabase.rollTo = 36;
                }
                if(str == selection[2]){
                    attendanceManagementSystemDatabase.setSubject = data3[0];
                    attendanceManagementSystemDatabase.setYear = data3[1];
                    attendanceManagementSystemDatabase.setDivision = data3[2];
                    attendanceManagementSystemDatabase.setBatch = data3[3];
                    attendanceManagementSystemDatabase.setCategory = "Practical";
                    attendanceManagementSystemDatabase.rollFrom = 36;
                    attendanceManagementSystemDatabase.rollTo = 54;
                }
                if(str == selection[3]){
                    attendanceManagementSystemDatabase.setSubject = data4[0];
                    attendanceManagementSystemDatabase.setYear = data4[1];
                    attendanceManagementSystemDatabase.setDivision = data4[2];
                    attendanceManagementSystemDatabase.setBatch = data4[3];
                    attendanceManagementSystemDatabase.setCategory = "Practical";
                    attendanceManagementSystemDatabase.rollFrom = 54;
                    attendanceManagementSystemDatabase.rollTo = 72;
                }
                if(str == selection[4]){
                    attendanceManagementSystemDatabase.setSubject = data5[0];
                    attendanceManagementSystemDatabase.setYear = data5[1];
                    attendanceManagementSystemDatabase.setDivision = data5[2];
                    attendanceManagementSystemDatabase.setBatch = data5[3];
                    attendanceManagementSystemDatabase.setCategory = "Practical";
                    attendanceManagementSystemDatabase.rollFrom = 72;
                    attendanceManagementSystemDatabase.rollTo = 90;
                }
                if(action.equals("Attendance")) {
                    Intent intent = new Intent(getContext(), NewAttendance.class);
                    startActivity(intent);
                }
                else if(action.equals("Report")) {
                    Intent intent = new Intent(getContext(), ViewReport.class);
                    startActivity(intent);
                }
            }
        });

        return root;
    }
}
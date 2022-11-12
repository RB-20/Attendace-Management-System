package com.example.ams.manage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ams.R;
import com.example.ams.database.AttendanceManagementSystemDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.CENTER;

public class ViewReport extends AppCompatActivity {
    public AttendanceManagementSystemDatabase attendanceManagementSystemDatabase;
    public String[][] attendanceData;
    public TableLayout tableA, tableB, tableC, tableD;
    public ScrollView scrollViewC, scrollViewD;
    public HorizontalScrollView horizontalScrollViewB, horizontalScrollViewD;
    public int[] cellWidth;
    //public int[] cellHeight;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        attendanceManagementSystemDatabase = new AttendanceManagementSystemDatabase(this);

        tableA = (TableLayout) findViewById(R.id.tableA);
        tableB = (TableLayout) findViewById(R.id.tableB);
        tableC = (TableLayout) findViewById(R.id.tableC);
        tableD = (TableLayout) findViewById(R.id.tableD);
        scrollViewC = (ScrollView) findViewById(R.id.scrollViewC);
        scrollViewD = (ScrollView) findViewById(R.id.scrollViewD);
        horizontalScrollViewB = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewB);
        horizontalScrollViewD = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewD);

        scrollViewC.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollViewD.scrollTo(scrollX,scrollY);
            }
        });
        scrollViewD.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollViewC.scrollTo(scrollX,scrollY);
            }
        });
        horizontalScrollViewB.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                horizontalScrollViewD.scrollTo(scrollX,scrollY);
            }
        });
        horizontalScrollViewD.setOnScrollChangeListener(new View.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                horizontalScrollViewB.scrollTo(scrollX,scrollY);
            }
        });

        attendanceData = attendanceManagementSystemDatabase.getReportData();
        createReport(attendanceData);
    }

    public void createReport(String[][] data){
        cellWidth = new int[data[0].length];
        // cellHeight = new int[data[0].length];

        TableRow tableRowA = new TableRow(this);
        tableRowA.addView(reportHeadCell(data[0][0],0));
        tableA.addView(tableRowA);

        TableRow tableRowB = new TableRow(this);
        for(int x=1; x<data[0].length; x++){
            tableRowB.addView(reportHeadCell(data[0][x],x));
        }
        tableB.addView(tableRowB);

        TableRow tableRowC;
        for(int x=1; x<data.length; x++){
            tableRowC = new TableRow(this);
            tableRowC.addView(reportBodyCell(data[x][0],0));
            tableC.addView(tableRowC);
        }

        TableRow tableRowD;
        for(int x=1; x<data.length; x++){
            tableRowD = new TableRow(this);
            for(int y=1; y<data[0].length; y++){
                tableRowD.addView(reportBodyCell(data[x][y],y));
            }
            tableD.addView(tableRowD);
        }
    }

    public TextView reportHeadCell(String str, int index){
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(2,2,2,2);

        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setGravity(CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.setPadding(5,5,5,5);
        textView.setLayoutParams(layoutParams);

        textView.measure(0,0);
        cellWidth[index] = textView.getMeasuredWidth();
        //  cellHeight[index] = textView.getMeasuredHeight();

        return textView;
    }

    public TextView reportBodyCell(String str, int index){
        //TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(cellWidth[index],cellHeight[index]);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(cellWidth[index],RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(2,2,2,2);

        TextView textView = new TextView(this);
        textView.setText(str);
        textView.setGravity(CENTER);
        if(index == 0) {
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundColor(Color.WHITE);
        }
        textView.setPadding(5,5,5,5);
        textView.setLayoutParams(layoutParams);

        return textView;
    }

    public void removeTables(){
        tableA.removeAllViews();
        tableB.removeAllViews();
        tableC.removeAllViews();
        tableD.removeAllViews();
    }

    public void detentionList(){
        removeTables();
        String[][] filteredData;
        int size = 0;
        for(int x = 1; x < attendanceData.length; x++){
            if(Float.parseFloat(attendanceData[x][1]) < 75)
                size++;
        }
        //filteredData = new String[size + 1][attendanceData[0].length];
        filteredData = new String[size + 1][4];
        int y = 0;
        //filteredData[y] = attendanceData[0];
        filteredData[y][0] = attendanceData[0][0];
        filteredData[y][1] = attendanceData[0][1];
        filteredData[y][2] = attendanceData[0][2];
        filteredData[y][3] = attendanceData[0][3];
        for(int x = 1; x < attendanceData.length; x++){
            if(Float.parseFloat(attendanceData[x][1]) < 75) {
                //   filteredData[++y] = attendanceData[x];
                filteredData[++y][0] = attendanceData[x][0];
                filteredData[y][1] = attendanceData[x][1];
                filteredData[y][2] = attendanceData[x][2];
                filteredData[y][3] = attendanceData[x][3];
            }
        }
        createReport(filteredData);
        Toast.makeText(getApplicationContext(),"Defaulters List",Toast.LENGTH_SHORT).show();
    }

    public void completeList(){
        removeTables();
        createReport(attendanceData);
        Toast.makeText(getApplicationContext(),"Complete List",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.report_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.defaulterList:
                if(attendanceData[0].length == 4)
                    Toast.makeText(this,"No attendance is taken yet",Toast.LENGTH_SHORT).show();
                else
                    detentionList();
                break;
            case R.id.completeReport:
                completeList();
                break;
        }
        return true;
    }

}
package com.example.ams.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.arraycopy;

import com.example.ams.main.ui.changepassword.ChangePasswordFragment;

public class AttendanceManagementSystemDatabase extends SQLiteOpenHelper {

    //DATABASE
    private static final String DB_NAME = "Attendance_Management_System_Database";
    private static final int DB_VERSION = 1;

    //TABLE
    private static final String DB_TABLE_USERDATA = "User_Data";
    private static final String DB_TABLE_LECTURE = "Lecture_Data";
    private static final String DB_TABLE_LECTURE_ATTENDANCE = "Lecture_Attendance";
    private static final String DB_TABLE_PRACTICAL = "Practical_Data";
    private static final String DB_TABLE_PRACTICAL_ATTENDANCE = "Practical_Attendance";

    //COLOUMS
    //  table - User_Data
    private static final String T_USERDATA_USER_EMAIL = "User_Email";
    private static final String T_USERDATA_USER_PASSWORD = "User_Password";

    //  table - Lecture_Data
    private static final String T_LECTURE_DATA_NAME = "Name";
    private static final String T_LECTURE_DATA_YEAR = "Year";
    private static final String T_LECTURE_DATA_DIVISION = "Division";

    // table - Lecture_Attendance
    private static final String T_LECTURE_ATTENDANCE_NAME = "Name";
    private static final String T_LECTURE_ATTENDANCE_YEAR = "Year";
    private static final String T_LECTURE_ATTENDANCE_DIVISION = "Division";
    private static final String T_LECTURE_ATTENDANCE_DATE = "Date";
    private static final String T_LECTURE_ATTENDANCE_ABSENT_LIST = "Absent_List";

    // table - Practical_Data
    private static final String T_PRACTICAL_DATA_NAME = "Name";
    private static final String T_PRACTICAL_DATA_YEAR = "Year";
    private static final String T_PRACTICAL_DATA_DIVISION = "Division";
    private static final String T_PRACTICAL_DATA_BATCH = "Batch";

    // table - Practical_Attendance
    private static final String T_PRACTICAL_ATTENDANCE_NAME = "Name";
    private static final String T_PRACTICAL_ATTENDANCE_YEAR = "Year";
    private static final String T_PRACTICAL_ATTENDANCE_DIVISION = "Division";
    private static final String T_PRACTICAL_ATTENDANCE_BATCH = "Batch";
    private static final String T_PRACTICAL_ATTENDANCE_DATE = "Date";
    private static final String T_PRACTICAL_ATTENDANCE_ABSENT_LIST = "Absent_List";



    //MANAGEMENT VARIABLES
    public static int index = 0;
    public static String mainChoice = "New Attendance";
    public static String setCategory = "";
    public static String setSubject = "";
    public static String setYear = "";
    public static String setDivision = "";
    public static String setBatch = "";
    public static int rollFrom = 0;
    public static int rollTo = 0;


    private Context otherContext;
    private SQLiteDatabase sqLiteDatabase;


    public AttendanceManagementSystemDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        otherContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //to create User_Data table
        String query1 = "CREATE TABLE " + DB_TABLE_USERDATA + " ("
                + T_USERDATA_USER_EMAIL + " TEXT PRIMARY KEY,"
                + T_USERDATA_USER_PASSWORD + " TEXT)";

        //to create Lecture Table
        String query2 = "CREATE TABLE " + DB_TABLE_LECTURE + " ("
                + T_LECTURE_DATA_NAME + " TEXT,"
                + T_LECTURE_DATA_YEAR + " TEXT,"
                + T_LECTURE_DATA_DIVISION + " TEXT)";

        String query3 = "CREATE TABLE " + DB_TABLE_LECTURE_ATTENDANCE + " ("
                + T_LECTURE_DATA_NAME + " TEXT,"
                + T_LECTURE_DATA_YEAR + " TEXT,"
                + T_LECTURE_DATA_DIVISION + " TEXT,"
                + T_LECTURE_ATTENDANCE_DATE + " TEXT,"
                + T_LECTURE_ATTENDANCE_ABSENT_LIST + " TEXT)";

        //to create Practical Table
        String query4 = "CREATE TABLE " + DB_TABLE_PRACTICAL + " ("
                + T_PRACTICAL_DATA_NAME + " TEXT,"
                + T_PRACTICAL_DATA_YEAR + " TEXT,"
                + T_PRACTICAL_DATA_DIVISION + " TEXT,"
                + T_PRACTICAL_DATA_BATCH + " TEXT)";

        String query5 = "CREATE TABLE " + DB_TABLE_PRACTICAL_ATTENDANCE + " ("
                + T_PRACTICAL_DATA_NAME + " TEXT,"
                + T_PRACTICAL_DATA_YEAR + " TEXT,"
                + T_PRACTICAL_DATA_DIVISION + " TEXT,"
                + T_PRACTICAL_DATA_BATCH + " TEXT,"
                + T_PRACTICAL_ATTENDANCE_DATE + " TEXT,"
                + T_PRACTICAL_ATTENDANCE_ABSENT_LIST + " TEXT)";


        //execute queries
        try {
            db.execSQL(query1);
            db.execSQL(query2);
            db.execSQL(query3);
            db.execSQL(query4);
            db.execSQL(query5);
            Log.i("Database","Table created");
        }catch (SQLException sqlException) {
            Log.i("Database","Table NOT created", sqlException);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query1 = "DROP TABLE IF EXISTS " + DB_TABLE_USERDATA;
        String query2 = "DROP TABLE IF EXISTS " + DB_TABLE_LECTURE;
        String query3 = "DROP TABLE IF EXISTS " + DB_TABLE_LECTURE_ATTENDANCE;
        String query4 = "DROP TABLE IF EXISTS " + DB_TABLE_PRACTICAL;
        String query5 = "DROP TABLE IF EXISTS " + DB_TABLE_PRACTICAL_ATTENDANCE;

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);

        onCreate(db);
    }

    //TODO: TO CHECK WHETHER ANY ACCOUNT IS CREATED
    public boolean isAnyUser(){
        boolean isUserExists = false;
        sqLiteDatabase = getReadableDatabase();
        String query1 = "SELECT * FROM "+DB_TABLE_USERDATA;

        try{
            Cursor cursor  = sqLiteDatabase.rawQuery(query1,null);
            while(cursor.moveToNext()) {
                String s = cursor.getString(0);
                if(s != null)
                    isUserExists = true;
            }
            cursor.close();
        }catch (SQLException sqlException){
            isUserExists = false;
        }
        return isUserExists;
    }

    //TODO: TO CREATE A NEW USER
    public boolean createNewUser(String email, String password) {
        boolean success = true;
        sqLiteDatabase = getWritableDatabase();
        String query1 = "INSERT INTO "+DB_TABLE_USERDATA+"("+T_USERDATA_USER_EMAIL+","
                +T_USERDATA_USER_PASSWORD+")"
                +" VALUES('"+email+"','" +password+"');";

        //execute queries
        try{
            sqLiteDatabase.execSQL(query1);
            success = true;
        }catch (SQLException sqlException) {
            Log.i("SQLExeption","Exception",sqlException);
            success = false;
        }

        return success;
    }

    //TODO: TO CHECK WHETHER THE USER IS VALID (EXISTS)
    public boolean isValidUser(String userEmail) {
        boolean isUserExists = false;
        sqLiteDatabase = getReadableDatabase();
        String query1 = "SELECT * FROM "+DB_TABLE_USERDATA+" WHERE "+T_USERDATA_USER_EMAIL+" = ?";

        try{
            Cursor cursor  = sqLiteDatabase.rawQuery(query1, new String[]{userEmail});
            while(cursor.moveToNext()) {
                String s = cursor.getString(0);
                if(s.equals(userEmail))
                    isUserExists = true;
            }
            cursor.close();
        }catch (SQLException sqlException){
            isUserExists = false;
        }
        return isUserExists;
    }

    //TODO: TO CHECK WHETHER THE USER PASSWORD IS VALID OR NOT
    public boolean isValidPassword(String password) {
        boolean isValidPassword = false;
        sqLiteDatabase = getReadableDatabase();
        String query1 = "SELECT * FROM "+DB_TABLE_USERDATA+" WHERE "+T_USERDATA_USER_PASSWORD+" = ?";

        try{
            Cursor cursor  = sqLiteDatabase.rawQuery(query1, new String[]{password});
            while(cursor.moveToNext()) {
                String s = cursor.getString(1);
                if(s.equals(password))
                    isValidPassword = true;
            }
            cursor.close();
        }catch (SQLException sqlException){
            isValidPassword = false;
        }
        return isValidPassword;
    }

//TODO:TO CHECK USERNAME AND PASSWORD IS VALID OR NOT
    public boolean isValidEmailAndPassword(String useremail,String password){

        sqLiteDatabase = getReadableDatabase();
        boolean res=false;
        String sql = "SELECT * FROM " + DB_TABLE_USERDATA;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while(cursor.moveToNext()){
            String res0 = cursor.getString(0);
            String res1 = cursor.getString(1);
            if(res0.equals(useremail)  && res1.equals(password)) {
                res=true;
                break;
            }
        }
        cursor.close();
        return res;

    }



    //TODO: TO GET USER'S PASSWORD
    public String getUserPassword(){
        sqLiteDatabase = getReadableDatabase();
        String pwd = "";
        String query1 = "SELECT * FROM "+DB_TABLE_USERDATA;

        try{
            Cursor cursor  = sqLiteDatabase.rawQuery(query1, null);
            while(cursor.moveToNext()) {
                String s = cursor.getString(1);
                pwd = s;
            }
            cursor.close();
        }catch (SQLException sqlException){}

        return pwd;
    }

    //TODO: TO CHANGE USER'S PASSWORD
    public boolean changeUserPassword(String pwd){
        boolean success = true;
        sqLiteDatabase = getWritableDatabase();
        String query1 = "UPDATE "+DB_TABLE_USERDATA+" SET "+T_USERDATA_USER_PASSWORD+"='"+pwd+"'";

        //execute queries
        try{
            sqLiteDatabase.execSQL(query1);
            success = true;
        }catch (SQLException sqlException) {
            Log.i("SQLExeption","Exception",sqlException);
            success = false;
        }

        return success;
    }

    //TODO: TO ADD NEW LECTURE
    public boolean createNewLectureSubject(String name, String year, String division) {
        Boolean success = true;
        sqLiteDatabase = getWritableDatabase();
        String query1 = "INSERT INTO " + DB_TABLE_LECTURE + "(" + T_LECTURE_DATA_NAME + ","
                + T_LECTURE_DATA_YEAR + ","
                + T_LECTURE_DATA_DIVISION + ")"
                + " VALUES('" + name + "','" + year + "','" + division + "');";

        //execute queries
        try {
            sqLiteDatabase.execSQL(query1);
            Toast.makeText(otherContext, "Account created", Toast.LENGTH_LONG).show();
            success = true;
        } catch (SQLException sqlException) {
            Log.i("SQLExeption", "Exception", sqlException);
            Toast.makeText(otherContext, "Error", Toast.LENGTH_LONG).show();
            success = false;
        }

        return success;
    }

    //TODO: TO GET THE LECTURE DATA FROM LECTURE TABLE
    public String[][] getLectureList() {
        sqLiteDatabase = getReadableDatabase();
        String query1 = "SELECT * FROM " + DB_TABLE_LECTURE;

        StringBuilder stringBuilderSubject = new StringBuilder();
        StringBuilder stringBuilderYear = new StringBuilder();
        StringBuilder stringBuilderDivision = new StringBuilder();

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query1, null);
            while (cursor.moveToNext()) {
                String subject = cursor.getString(0);
                String year = cursor.getString(1);
                String division = cursor.getString(2);

                arrayList.add(subject);
                arrayList.add(year);
                arrayList.add(division);
            }
            cursor.close();
        } catch (SQLException sqlException) {
            Log.i("SQLExeption", "Exception", sqlException);
        }

        //Converting a data from arraylist into String format & storing in the form of rows and cols
        String subjectArray[][] = new String[50][3];
        index = 0;
        for (int i = 0; i < (arrayList.size() / 3); i++) {
            for (int j = 0; j < 3; j++) {
                subjectArray[i][j] = arrayList.get(index);
                index++;
            }
        }
        // Toast.makeText(otherContext,""+index,Toast.LENGTH_LONG).show();
        return subjectArray;
    }

    //TODO: TO ADD NEW PRACTICAL
    public boolean createNewPracticalSubject(String name, String year, String division) {
        Boolean success = true;
        sqLiteDatabase = getWritableDatabase();
        for (int x = 1; x <= 5; x++) {
            String query1 = "INSERT INTO " + DB_TABLE_PRACTICAL + "(" + T_PRACTICAL_DATA_NAME + ","
                    + T_PRACTICAL_DATA_YEAR + ","
                    + T_PRACTICAL_DATA_DIVISION + ","
                    + T_PRACTICAL_DATA_BATCH + ")"
                    + " VALUES('" + name + "','" + year + "','" + division + "','" + x + "');";

            //execute queries
            try {
                sqLiteDatabase.execSQL(query1);
                //Toast.makeText(otherContext, "Account created", Toast.LENGTH_LONG).show();
                success = true;
            } catch (SQLException sqlException) {
                Log.i("SQLExeption", "Exception", sqlException);
                Toast.makeText(otherContext, "Error", Toast.LENGTH_LONG).show();
                success = false;
            }
        }
        return success;
    }

    //TODO: TO GET THE PRACTICAL DATA FROM PRACTICAL TABLE
    public String[][] getPracticalList() {
        sqLiteDatabase = getReadableDatabase();
        String query1 = "SELECT * FROM " + DB_TABLE_PRACTICAL;

        StringBuilder stringBuilderSubject = new StringBuilder();
        StringBuilder stringBuilderYear = new StringBuilder();
        StringBuilder stringBuilderDivision = new StringBuilder();

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query1, null);
            while (cursor.moveToNext()) {
                String subject = cursor.getString(0);
                String year = cursor.getString(1);
                String division = cursor.getString(2);
                String batch = cursor.getString(3);

                arrayList.add(subject);
                arrayList.add(year);
                arrayList.add(division);
                arrayList.add(batch);
            }
            cursor.close();
        } catch (SQLException sqlException) {
            Log.i("SQLExeption", "Exception", sqlException);
        }

        String subjectArray[][] = new String[50][4];
        index = 0;
        for (int i = 0; i < (arrayList.size() / 4); i++) {
            for (int j = 0; j < 4; j++) {
                subjectArray[i][j] = arrayList.get(index);
                index++;
            }
        }
        // Toast.makeText(otherContext,""+index,Toast.LENGTH_LONG).show();
        return subjectArray;
    }

    //TODO: TO ADD NEW ATTENDANCE
    public boolean addNewAttendance(String date, String absent) {
        boolean saved = false;
        sqLiteDatabase = getWritableDatabase();
        String query1 = null;
        if (setCategory.equals("Lecture")) {
            query1 = "INSERT INTO " + DB_TABLE_LECTURE_ATTENDANCE + "("
                    + T_LECTURE_DATA_NAME + ","
                    + T_LECTURE_ATTENDANCE_YEAR + ","
                    + T_LECTURE_ATTENDANCE_DIVISION + ","
                    + T_LECTURE_ATTENDANCE_DATE + ","
                    + T_LECTURE_ATTENDANCE_ABSENT_LIST + ")"
                    + " VALUES('" + setSubject + "',"
                    + "'" + setYear + "',"
                    + "'" + setDivision + "',"
                    + "'" + date + "',"
                    + "'" + absent + "');";
        } else {
            query1 = "INSERT INTO " + DB_TABLE_PRACTICAL_ATTENDANCE + "("
                    + T_PRACTICAL_DATA_NAME + ","
                    + T_PRACTICAL_ATTENDANCE_YEAR + ","
                    + T_PRACTICAL_ATTENDANCE_DIVISION + ","
                    + T_PRACTICAL_ATTENDANCE_BATCH + ","
                    + T_PRACTICAL_ATTENDANCE_DATE + ","
                    + T_PRACTICAL_ATTENDANCE_ABSENT_LIST + ")"
                    + " VALUES('" + setSubject + "',"
                    + "'" + setYear + "',"
                    + "'" + setDivision + "',"
                    + "'" + setBatch + "',"
                    + "'" + date + "',"
                    + "'" + absent + "');";
        }

        //execute queries
        try {
            sqLiteDatabase.execSQL(query1);
            saved = true;
        } catch (SQLException sqlException) {
            Log.i("SQLExeption", "Exception", sqlException);
            saved = false;
        }

        return saved;
    }

    //TODO: TO GET THE ATTENDANCE DATA
    public String[][] getReportData() {
        sqLiteDatabase = getReadableDatabase();
        String query1;
        ArrayList<String> arrayListHead = new ArrayList<>();
        ArrayList<String> absentList = new ArrayList<>();

        try {
            if (setCategory.equalsIgnoreCase("Lecture")) {
                query1 = "SELECT * FROM " + DB_TABLE_LECTURE_ATTENDANCE;
                arrayListHead.add("Roll no.");
                arrayListHead.add("Percentage (%)");
                arrayListHead.add("Present Count");
                arrayListHead.add("Absent Count");

                Cursor cursor = sqLiteDatabase.rawQuery(query1, null);
                while (cursor.moveToNext()) {
                    String subject = cursor.getString(0);
                    String year = cursor.getString(1);
                    String division = cursor.getString(2);
                    String date = cursor.getString(3);
                    String absent = cursor.getString(4);
                    if (subject.equals(setSubject) && year.equals(setYear) && division.equals(setDivision)) {
                        arrayListHead.add(date);
                        absentList.add(absent + "#");
                    }
                }
                cursor.close();
            } else {
                query1 = "SELECT * FROM " + DB_TABLE_PRACTICAL_ATTENDANCE;
                arrayListHead.add("Roll no.");
                arrayListHead.add("Percentage (%)");
                arrayListHead.add("Present Count");
                arrayListHead.add("Absent Count");

                Cursor cursor = sqLiteDatabase.rawQuery(query1, null);
                while (cursor.moveToNext()) {
                    String subject = cursor.getString(0);
                    String year = cursor.getString(1);
                    String division = cursor.getString(2);
                    String batch = cursor.getString(3);
                    String date = cursor.getString(4);
                    String absent = cursor.getString(5);
                    if (subject.equals(setSubject) && year.equals(setYear) && division.equals(setDivision) && batch.equals(setBatch)) {
                        arrayListHead.add(date);
                        absentList.add(absent + "#");
                    }
                }
                cursor.close();
            }
        } catch (SQLException sqlException) {
            Log.i("SQLExeption", "Exception", sqlException);
        }

        String str1 = absentList.toString();
        String str2 = str1.substring(1, str1.length() - 1);
        str2 = str2.replaceAll("\\s", "");
        String[] filterList = str2.split("#");

        String[][] body = new String[rollTo - rollFrom + 1][];
        int bodyCount = 0;

        for (int roll = rollFrom; roll <= rollTo; roll++) {
            String[] status = new String[filterList.length];
            for (int columns = 0; columns < filterList.length; columns++) {
                String[] str3 = filterList[columns].split(",");
                int was = 0;
                for (int x = 0; x < str3.length; x++) {
                    try {
                        if (roll == Integer.parseInt(str3[x]))
                            was++;
                    } catch (Exception e) {
                    }
                }
                if (was != 0)
                    status[columns] = "A";
                else
                    status[columns] = "P";
            }
            int absentCount = 0;
            for (int y = 0; y < status.length; y++) {
                if (status[y].equals("A"))
                    absentCount++;
            }

            //To display data in well formatted manner in tabular form
            if (!absentList.isEmpty()) {
                float percentage = (((float) (filterList.length - absentCount) / filterList.length) * 100);
                int presentCount = filterList.length - absentCount;

                //String[] b1 = {"" + String.format("%.2f",percentage) + " %", "" + presentCount, "" + absentCount};
                String[] b1 = {"" + roll, "" + String.format("%.2f", percentage), "" + presentCount, "" + absentCount};
                String[] finalList = new String[status.length + b1.length];

                arraycopy(b1, 0, finalList, 0, b1.length);
                arraycopy(status, 0, finalList, b1.length, status.length);

                body[bodyCount] = new String[finalList.length];
                arraycopy(finalList, 0, body[bodyCount], 0, finalList.length);
                bodyCount++;
            } else {
                absentCount = 0;
                float percentage = 0;
                int presentCount = 0;

                String[] b1 = {"" + roll, "" + percentage, "" + presentCount, "" + absentCount};
                String[] finalList = new String[status.length + b1.length];

                System.arraycopy(b1, 0, finalList, 0, b1.length);
                System.arraycopy(status, 0, finalList, b1.length, status.length);

                body[bodyCount] = new String[finalList.length];
                arraycopy(finalList, 0, body[bodyCount], 0, finalList.length);
                bodyCount++;
            }

        }

        String[][] data = new String[rollTo - rollFrom + 2][arrayListHead.size()];
        Object[] obj = arrayListHead.toArray();
        data[0] = Arrays.copyOf(obj, obj.length, String[].class);

        for (int x = 1; x < data.length; x++) {
            data[x] = body[x - 1];
        }

        return data;
    }
    //================================================================================================================//


//================================================================================================================//
}




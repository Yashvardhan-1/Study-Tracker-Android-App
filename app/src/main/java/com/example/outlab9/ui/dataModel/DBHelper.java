package com.example.outlab9.ui.dataModel;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase";
    private static final String STUDY_TABLE = "studyTable";
    private static final String ASSIGNMENT_TABLE = "assignmentTable";
    private static final String EXAM_TABLE = "examTable";
    private static final String LECTURE_TABLE = "lectureTable";

    private static final String name_col = "name";
    private static final String course_col = "course";
    private static final String date_col = "date";
    private static final String time_col = "time";
    private static final String description_col = "description";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String study = "CREATE TABLE " + STUDY_TABLE + "("+name_col+" TEXT PRIMARY KEY,"+course_col+" TEXT," +
                ""+description_col+" TEXT,"+time_col+" TEXT,"+date_col+" TEXT,type INTEGER )";
        String assignment = "CREATE TABLE " + ASSIGNMENT_TABLE + "("+name_col+" TEXT PRIMARY KEY,"+course_col+" TEXT," +
                ""+description_col+" TEXT,"+time_col+" TEXT,"+date_col+" TEXT,type INTEGER )";
        String exam = "CREATE TABLE " + EXAM_TABLE + "("+name_col+" TEXT PRIMARY KEY,"+course_col+" TEXT," +
                ""+description_col+" TEXT,"+time_col+" TEXT,"+date_col+" TEXT,type INTEGER )";
        String lecture = "CREATE TABLE " + LECTURE_TABLE + "("+name_col+" TEXT PRIMARY KEY,"+course_col+" TEXT," +
                ""+description_col+" TEXT,"+time_col+" TEXT,"+date_col+" TEXT,type INTEGER )";


//        String study = "CREATE TABLE " + STUDY_TABLE + "(name TEXT PRIMARY KEY,course TEXT," +
//                "description TEXT,deadlineTime TEXT,deadlineDate TEXT,type INTEGER )";
//        String assignment = "CREATE TABLE " + ASSIGNMENT_TABLE + "(name TEXT PRIMARY KEY,course TEXT," +
//                "description TEXT,deadlineTime TEXT,deadlineDate TEXT,type INTEGER )";
//        String exam = "CREATE TABLE " + EXAM_TABLE + "(name TEXT PRIMARY KEY,course TEXT," +
//                "description TEXT,deadlineTime TEXT,deadlineDate TEXT,type INTEGER )";
//        String lecture = "CREATE TABLE " + LECTURE_TABLE + "(name TEXT PRIMARY KEY,course TEXT," +
//                "description TEXT,deadlineTime TEXT,deadlineDate TEXT,type INTEGER )";

        db.execSQL(study);
        db.execSQL(assignment);
        db.execSQL(exam);
        db.execSQL(lecture);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ STUDY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ ASSIGNMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ EXAM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ LECTURE_TABLE);

        onCreate(db);
    }

    public boolean insertData (int type, String name, String course, String deadlineTime,
                               String deadlineDate, String description){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(name_col, name);
        contentValues.put(course_col, course);
        contentValues.put(description_col, description);
        contentValues.put(date_col, deadlineDate);
        contentValues.put(time_col, deadlineTime);
        contentValues.put("type", type);

        long result;

        switch (type){
            case 0:
                result = sqLiteDatabase.insert(STUDY_TABLE, null, contentValues);
                break;
            case 1:
                result = sqLiteDatabase.insert(ASSIGNMENT_TABLE, null, contentValues);
                break;
            case 2:
                result = sqLiteDatabase.insert(EXAM_TABLE, null, contentValues);
                break;
            case 3:
                result = sqLiteDatabase.insert(LECTURE_TABLE, null, contentValues);
                break;
            default: result = -1;
        }

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


    @SuppressLint("Range")
    public ArrayList<DataModel> getData(int type){

        ArrayList<DataModel> arrayList = new ArrayList<DataModel>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor;
        switch (type){
            case 0:
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + STUDY_TABLE, null);
                break;
            case 1:
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ASSIGNMENT_TABLE, null);
                break;
            case 2:
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + EXAM_TABLE, null);
                break;
            case 3:
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + LECTURE_TABLE, null);
                break;
            default:
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ASSIGNMENT_TABLE, null);
        }

        cursor.moveToFirst();

        String name;
        String description;
        String deadlineDate;
        String deadlineTime;
        String course;

        while(!cursor.isAfterLast()){
            name = cursor.getString(cursor.getColumnIndex(name_col));
            course = cursor.getString(cursor.getColumnIndex(course_col));
            description = cursor.getString(cursor.getColumnIndex(description_col));
            deadlineDate = cursor.getString(cursor.getColumnIndex(date_col));
            deadlineTime = cursor.getString(cursor.getColumnIndex(time_col));

            arrayList.add(new DataModel(name,description,deadlineDate,deadlineTime,course,type));

            cursor.moveToNext();
        }
        cursor.close();

        return arrayList;
    }

    public boolean deleteData(DataModel data){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int type = data.getType();

        int result = 0;
        switch (type){
            case 0:
                result = sqLiteDatabase.delete(STUDY_TABLE, name_col +" = ?", new String[]{data.getName()});
                break;
            case 1:
                result = sqLiteDatabase.delete(ASSIGNMENT_TABLE, name_col +" = ?", new String[]{data.getName()});
                break;
            case 2:
                result = sqLiteDatabase.delete(EXAM_TABLE, name_col +" = ?", new String[]{data.getName()});
                break;
            case 3:
                result = sqLiteDatabase.delete(LECTURE_TABLE, name_col +" = ?", new String[]{data.getName()});
                break;
        }

        if(result == 1){
            return true;
        }else{
            return false;
        }
    }

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int[] dataForCalendar (LocalDate date){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");

        String dateTxt = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String dateFromDatabase;

        int[] array = new int[4];
        for (int i = 0; i < 4; i++) {
            array[i] = 0;
        }


        //////////////////////////////////////////////////////////
        Cursor cursor_study = sqLiteDatabase.rawQuery("SELECT * FROM " + STUDY_TABLE, null);
        cursor_study.moveToFirst();
        while(!cursor_study.isAfterLast()){
            dateFromDatabase = cursor_study.getString(cursor_study.getColumnIndex(date_col));

            if(dateTxt.contains(dateFromDatabase)){
                array[0]++;
            }
            cursor_study.moveToNext();
        }
        cursor_study.close();

        //////////////////////////////////////////////////////////
        Cursor cursor_assignment = sqLiteDatabase.rawQuery("SELECT * FROM " + ASSIGNMENT_TABLE, null);
        cursor_assignment.moveToFirst();
        while(!cursor_assignment.isAfterLast()){
            dateFromDatabase = cursor_assignment.getString(cursor_assignment.getColumnIndex(date_col));

            if(dateTxt.contains(dateFromDatabase)){
                array[1]++;
            }
            cursor_assignment.moveToNext();
        }
        cursor_assignment.close();

        //////////////////////////////////////////////////////////
        Cursor cursor_exam = sqLiteDatabase.rawQuery("SELECT * FROM " + EXAM_TABLE, null);
        cursor_exam.moveToFirst();
        while(!cursor_exam.isAfterLast()){
            dateFromDatabase = cursor_exam.getString(cursor_exam.getColumnIndex(date_col));

            if(dateTxt.contains(dateFromDatabase)){
                array[2]++;
            }
            cursor_exam.moveToNext();
        }
        cursor_exam.close();

        //////////////////////////////////////////////////////////
        Cursor cursor_lecture = sqLiteDatabase.rawQuery("SELECT * FROM " + LECTURE_TABLE, null);
        cursor_lecture.moveToFirst();
        while(!cursor_lecture.isAfterLast()){
            dateFromDatabase = cursor_lecture.getString(cursor_lecture.getColumnIndex(date_col));

            if(dateTxt.contains(dateFromDatabase)){
                array[3]++;
            }
            cursor_lecture.moveToNext();
        }
        cursor_lecture.close();


        return array;
    }
}

package com.example.todolistingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "task_table";
    public static final String  ID = "ID";
    public static final String TASKS = "TASKS";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASKS TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public void insertData(String task){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASKS,task);
         db.insertWithOnConflict(TABLE_NAME, null, cv,SQLiteDatabase.CONFLICT_REPLACE);
         db.close();

    }

    public void deleteData (String task){
        SQLiteDatabase db =this.getWritableDatabase();
       db.delete(TABLE_NAME,"TASk = ?",new String[]{task});
       db.close();

    }


    public ArrayList<String> getAllData(){
        ArrayList<String> todoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{TASKS},null,null,null,null,null);


               while (cursor.moveToNext()) {
                   int index = cursor.getColumnIndex("TASKS");
                   todoList.add(cursor.getString(index));
               }

               cursor.close();
               db.close();
        return todoList;

    }



}

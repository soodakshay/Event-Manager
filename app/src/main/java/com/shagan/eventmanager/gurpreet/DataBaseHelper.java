package com.shagan.eventmanager.gurpreet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;


public class DataBaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "MyEvents.db";
    static final String TABLE_NAME = "MyEvents";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "Title";
    public static final String COL_3 = "Description";
    public static final String COL_4 = "Venue";
    public static final String COL_5 = "Latitude";
    public static final String COL_6 = "Longitude";
    public static final String COL_7 = "Address";
    public static final String COL_8 = "Date";
    public static final String COL_9 = "Time";
    public static final String COL_10 = "Day_Of_Month";
    public static final String COL_11 = "Month";
    public static final String COL_12 = "hour";
    public static final String COL_13 = "min";
    SQLiteDatabase myDB;

    int date, month;
    Calendar c;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY," + COL_2 + " TEXT," + COL_3 + " TEXT,"
                + COL_4 + " TEXT," + COL_5 + " TEXT," + COL_6 + " TEXT," + COL_7 + " TEXT," + COL_8 + " TEXT," + COL_9 + " TEXT," + COL_10 + " INTEGER ," + COL_11 + " INTEGER, " + COL_12 + " INTEGER, " + COL_13 + " INTEGER )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exist " + TABLE_NAME);
        onCreate(db);

    }


    public Integer InsertValues(String title, String Desc, String Venue, String Lat, String Long, String Addr, String Date, String Time, int DayOfMonth, int MonthName)

    {

        myDB = this.getWritableDatabase();

        ContentValues cV = new ContentValues();
        cV.put(COL_2, title);
        cV.put(COL_3, Desc);
        cV.put(COL_4, Venue);
        cV.put(COL_5, Lat);
        cV.put(COL_6, Long);
        cV.put(COL_7, Addr);
        cV.put(COL_8, Date);
        cV.put(COL_9, Time);
        cV.put(COL_10, DayOfMonth);
        cV.put(COL_11, MonthName);
        cV.put(COL_12, Time.split(":")[0]);
        cV.put(COL_13, Time.split(":")[1]);
        long isInserted = myDB.insert(TABLE_NAME, null, cV);
        if (isInserted == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public int checkBrowse() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cyrsor = db.rawQuery("Select * From " + TABLE_NAME, null);
        int flag = 0;
        if (cyrsor.moveToFirst()) {
            flag = 0;
        } else {
            flag = 1;
        }
        return flag;


    }

    public Cursor getData() {

        c = Calendar.getInstance();
        date = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        month = month + 1;
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + TABLE_NAME + " Where " + "(" + COL_10 + " > " + date + " AND " + COL_11 + " >= " + month + ")" + " OR (" + COL_10 + " < " +
                date + " AND " + COL_11 + " > " + month + " ) " + " OR " + "(" + COL_10 + " >= " + date + " AND " + COL_11 + " >= " + month + " AND " + COL_12 + " >= " + hour + " AND " + COL_13 + " > " + minute + " ) " + " ORDER BY " + COL_10 + " ASC", null);
        return res;
    }


    public Cursor getDataExpired() {
        c = Calendar.getInstance();
        date = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        month = month + 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + TABLE_NAME + " Where ( " + COL_10 + " < " + date + " AND " + COL_11 + " <= " + month + " ) OR ( " + COL_10 + " > " + date + " AND " +
                COL_11 + " < " + month + " ) ORDER BY " + COL_10 + " DESC", null);
        return res;
    }

    public Cursor getSpecificData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + "=" + id, null);
        return cursor;
    }

    public void updateDB(int id, String title, String Desc, String Venue, String Lat, String Long, String Addr, String Date, String Time, int DayOfMonth, int MonthName)

    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("UPDATE " + TABLE_NAME + " SET " + COL_2 + "=" + "'" + title + "'" + ","
                + COL_3 + "=" + "'" + Desc + "'" + "," + COL_4 + "=" + "'" + Venue + "'" + "," + COL_5 + "=" + "'" + Lat + "'" + "," + COL_6 + "=" + "'" + Long + "'" + "," + COL_7 + "=" + "'" + Addr + "'" + "," + COL_8 + "=" + "'" + Date + "'" + "," + COL_9 + "=" + "'" + Time + "'" + "," + COL_10 + "=" + DayOfMonth + "," + COL_11 + "=" + MonthName + " WHERE " + COL_1 + "=" + id);
    }

    public Cursor getSimpleData()

    {
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From " + TABLE_NAME + " Where " + "(" + COL_10 + " >= " + date + " AND " + COL_11 + " >= " + month + " AND " + COL_12 + " >= " + hour + " AND " + COL_13 + " > " + minute + " ) ", null);
        return res;
    }


}

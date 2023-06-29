package com.example.samoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class db {
    public SQLiteDatabase database = null;
    private final static String DATABASE_NAME = "db.db";
    private static String create_table;
    private Context mCtx;
    public db(Context ctx) {
        this.mCtx = ctx;
    }

    public void open(int year, int month) throws SQLException {
        String table_name = "t" + year + "y" + month + "m";
        create_table = "CREATE TABLE " + table_name + "(_id INTEGER PRIMARY KEY, date INTEGER, time INTEGER, type TEXT, name TEXT, store TEXT, cost INTEGER)";
        database = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            database.execSQL(create_table);
        } catch (Exception err) {
        }
    }

    public Cursor getAll(int year, int month) {
        String table_name = "t" + year + "y" + month + "m";
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + table_name + " ORDER by date ASC, time ASC", null);
        return cursor;
    }

    public Cursor get(int id, int year, int month) {
        String table_name = "t" + year + "y" + month + "m";
        Cursor cursor;
        cursor = database.query(table_name, new String[] {"_id", "date", "time", "type", "name", "store", "cost"}, "_id = " + id, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor sort(int year, int month, String sort_by) {
        String table_name = "t" + year + "y" + month + "m";
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + table_name + " ORDER by date " + sort_by +", time " + sort_by, null);    //SELECT * FROM t{year}y{month} ORDER by date ASC|DESC, time ASC|DESC
        return cursor;
    }

    public void clear(int year, int month) {
        String table_name = "t" + year + "y" + month + "m";
        database.delete(table_name, null, null);
    }

    public void insert(int year, int month, int date, int time, String type, String name, String store, int cost) {
        String table_name = "t" + year + "y" + month + "m";
        ContentValues args = new ContentValues();
        args.put("date", date);
        args.put("time", time);
        args.put("type", type);
        args.put("name", name);
        args.put("store", store);
        args.put("cost", cost);
        database.insert(table_name, null, args);
    }

    public void update(int id, int year, int month, int date, int time, String type, String name, String store, int cost) {
        String table_name = "t" + year + "y" + month + "m";
        ContentValues args = new ContentValues();
        args.put("date", date);
        args.put("time", time);
        args.put("type", type);
        args.put("name", name);
        args.put("store", store);
        args.put("cost", cost);
        database.update(table_name, args, "_id = " + id, null);
    }

    public void delete(int id, int year, int month) {
        String table_name = "t" + year + "y" + month + "m";
        database.delete(table_name, "_id = " + id, null);
    }

    public String convertToTimeString(int min) {
        int hour = min / 60;
        min = min % 60;
        if(min < 10)
            return hour + ":0" + min;
        else
            return hour + ":" + min;
    }

    public int convertToMinInt(int hr, int m) {
        int t = hr * 60 + m;
        return t;
    }

    public void addTestItem() {
        ContentValues args = new ContentValues();
        args.put("date", 23);
        args.put("time", 382);
        args.put("type", "transportation");
        args.put("name", "test");
        args.put("store", "t");
        args.put("cost", 100);
        database.insert("t2023y6m", null, args);
        args.put("date", 20);
        args.put("time", 620);
        args.put("type", "food");
        args.put("name", "lunch");
        args.put("store", "tasty");
        args.put("cost", 1370);
        database.insert("t2023y6m", null, args);
    }
}

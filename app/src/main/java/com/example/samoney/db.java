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
        create_table = "CREATE TABLE " + table_name +
                "(_id INTEGER PRIMARY KEY, date INTEGER, time INTEGER, type TEXT, name TEXT, store TEXT, cost INTEGER)";
        database = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            database.execSQL(create_table);
        } catch (Exception exception) {
        }
    }

    public Cursor getAll(int year, int month) {
        String table_name = "t" + year + "y" + month + "m";
        Cursor cursor;
        cursor = database.query(table_name, new String[] {"_id", "date", "time", "type", "name", "store", "cost"}, null, null, null, null, null, null);
        return cursor;
    }

    public void clear(int year, int month) {
        String table_name = "t" + year + "y" + month + "m";
        database.delete(table_name, null, null);
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

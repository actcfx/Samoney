package com.example.samoney;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class db {
    public SQLiteDatabase database = null;
    final static String DATABASE_NAME = "database.db";
    static String year, month;
    static String table_name;
    static String create_table;
    Cursor cursor;
    private Context mCtx;
    public db(Context ctx) {
        this.mCtx = ctx;
    }

    public void open(int y, int m) throws SQLException {
        month = Integer.toString(m);
        year = Integer.toString(y);
        table_name = "t" + year + "y" + month + "m";
        create_table = "CREATE TABLE " + table_name +
                "(_id INTEGER PRIMARY KEY, date INTEGER, time INTEGER, type TEXT, item TEXT, store TEXT, cost INTEGER)";
        database = mCtx.openOrCreateDatabase(DATABASE_NAME, 0,null);
        try {
            database.execSQL(create_table);
        } catch (Exception exception) {
        }
    }
}

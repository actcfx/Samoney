package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private db database = null;
    private int now_year = 2023, now_month = 6;
    private ListAdapter list_adapter;
    private Cursor cursor;
    TextView month_title;
    Button pre_month_btn, next_month_btn;
    ListView list_view;
    BottomNavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        month_title = findViewById(R.id.month_title);
        pre_month_btn = findViewById(R.id.pre_month_btn);
        next_month_btn = findViewById(R.id.next_month_btn);
        nav_view = findViewById(R.id.nav_view);
        list_view = findViewById(R.id.list_view);

        pre_month_btn.setOnClickListener(onClickListener);
        next_month_btn.setOnClickListener(onClickListener);

        database = new db(this);
        database.open(now_year, now_month);
        database.clear(now_year, now_month);
        database.addTestItem();
        cursor = database.getAll(now_year, now_month);
        updateAdapter(cursor);

        nav_view.getMenu().setGroupCheckable(0, false, false);
        nav_view.getMenu().getItem(1).setEnabled(false);
        nav_view.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.log_out_nav: {
                    new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("確定要登出嗎")
                            .setPositiveButton("確定", (dialogInterface, i) -> {
                                Toast toast = Toast.makeText(MainActivity.this, "掰掰，北極狐！", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, LogIn.class);
                                startActivity(intent);
                            })
                            .setNegativeButton("取消", (dialogInterface, i) -> { })
                            .show();
                    break;
                }
            }
            return true;
        });
    }

    View.OnClickListener onClickListener = view -> {
        switch(view.getId()) {
            case R.id.pre_month_btn: {
                this.now_month --;
                if(now_month < 1) {
                    this.now_year --;
                }
                database.open(now_year, now_month);
                cursor = database.getAll(now_year, now_month);
                updateAdapter(cursor);
                month_title.setText(now_year + "/" + now_month);
                break;
            } case R.id.next_month_btn: {
                this.now_month ++;
                if(now_month > 12) {
                    this.now_year ++;
                }
                database.open(now_year, now_month);
                cursor = database.getAll(now_year, now_month);
                updateAdapter(cursor);
                month_title.setText(now_year + "/" + now_month);
                break;
            }
        }
    };

    public String convertTime(int min) {
        int hour = min / 60;
        min = min % 60;
        return hour + ":" + min;
    }

    @SuppressLint("ResourceType")
    private void updateAdapter(Cursor cursor) {
        if(cursor != null && cursor.getCount() >= 0) {
            List<Map<String, Object>> item_list = new ArrayList<>();
            TypedArray icon_list = getResources().obtainTypedArray(R.array.icon_list);

            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++) {
                Map<String, Object> item = new HashMap<>();
                item.put("date", cursor.getInt(1));
                item.put("time", convertTime(cursor.getInt(2)));
                switch (cursor.getString(3)) {
                    case "transportation": {
                        item.put("type_icon", icon_list.getResourceId(0, 0));
                        break;
                    } case "food": {
                        item.put("type_icon", icon_list.getResourceId(1, 0));
                        break;
                    } case "shopping": {
                        item.put("type_icon", icon_list.getResourceId(2, 0));
                        break;
                    } case "home": {
                        item.put("type_icon", icon_list.getResourceId(3, 0));
                        break;
                    }
                }
                item.put("name", cursor.getString(4));
                item.put("store", cursor.getString(5));
                item.put("cost", cursor.getString(6));
                item_list.add(item);
                cursor.moveToNext();
            }

            list_adapter = new ListAdapter(MainActivity.this, item_list);
            list_view.setAdapter(list_adapter);
        }
    }
}
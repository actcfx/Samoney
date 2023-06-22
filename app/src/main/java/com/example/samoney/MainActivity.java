package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private db database = null;
    Cursor cursor;
    int now_year = 2023, now_month = 6;
    TextView month_title;
    Button pre_month_btn, next_month_btn;
    BottomNavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        month_title = findViewById(R.id.month_title);
        pre_month_btn = findViewById(R.id.pre_month_btn);
        next_month_btn = findViewById(R.id.next_month_btn);
        nav_view =  findViewById(R.id.nav_view);

        pre_month_btn.setOnClickListener(onClickListener);
        next_month_btn.setOnClickListener(onClickListener);

        database = new db(this);
        database.open(now_year, now_month);
//        cursor = database.getAll();
//        updateAdapter(cursor);

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

        }
    };
}
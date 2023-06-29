package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class EditItem extends AppCompatActivity {

    private db database = null;
    private Calendar calendar = Calendar.getInstance();
    ImageButton trans_img_btn, food_img_btn, shopping_img_btn, home_img_btn;
    ImageView type_img;
    EditText type_edittxt, cost_edittxt, store_edittxt, name_edittxt, date_edittxt, time_edittxt;
    Button cancel_btn, update_btn, delete_btn, copy_btn;

    private int current_year, current_month, current_day, current_hour, current_min;
    private int pre_year, year, pre_month, month, day, hour, min, time;
    private String time_str;
    private String type = "transportation";
    private String name = " ";
    private String store = " ";
    private int cost = 0;
    private int id = 0;

    Cursor cursor;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        trans_img_btn = findViewById(R.id.edit_trans_img_btn);
        food_img_btn = findViewById(R.id.edit_food_img_btn);
        shopping_img_btn = findViewById(R.id.edit_shopping_ing_btn);
        home_img_btn = findViewById(R.id.edit_home_img_btn);
        type_img = findViewById(R.id.edit_type_img);
        type_edittxt = findViewById(R.id.edit_type_edittxt);
        cost_edittxt = findViewById(R.id.edit_cost_edittxt);
        store_edittxt = findViewById(R.id.edit_store_edittxt);
        name_edittxt = findViewById(R.id.edit_name_edittxt);
        date_edittxt = findViewById(R.id.edit_date_edittxt);
        time_edittxt = findViewById(R.id.edit_time_edittxt);
        cancel_btn = findViewById(R.id.edit_cancel_btn);
        update_btn = findViewById(R.id.edit_update_btn);
        delete_btn = findViewById(R.id.edit_delete_btn);
        copy_btn = findViewById(R.id.edit_copy_btn);

        date_edittxt.setInputType(InputType.TYPE_NULL);
        time_edittxt.setInputType(InputType.TYPE_NULL);

        this.current_year = calendar.get(Calendar.YEAR);
        this.current_month = calendar.get(Calendar.MONTH);
        this.current_day = calendar.get(Calendar.DAY_OF_MONTH);
        this.current_hour = calendar.get(Calendar.HOUR);
        this.current_min = calendar.get(Calendar.MINUTE);

        this.id = getIntent().getIntExtra("id", 0);
        this.pre_year = getIntent().getIntExtra("year", 0);
        this.pre_month = getIntent().getIntExtra("month", 0);

        database = new db(this);
        database.open(pre_year, pre_month);
        getItemInfo();
        setItem();

        trans_img_btn.setOnClickListener(onClickListener);
        food_img_btn.setOnClickListener(onClickListener);
        shopping_img_btn.setOnClickListener(onClickListener);
        home_img_btn.setOnClickListener(onClickListener);
        date_edittxt.setOnClickListener(onClickListener);
        time_edittxt.setOnClickListener(onClickListener);
        copy_btn.setOnClickListener(onClickListener);
        cancel_btn.setOnClickListener(onClickListener);
        delete_btn.setOnClickListener(onClickListener);
        update_btn.setOnClickListener(onClickListener);
    }

    @SuppressLint("ResourceType")
    View.OnClickListener onClickListener = view -> {
        TypedArray icon_list = getResources().obtainTypedArray(R.array.icon_list);

        switch (view.getId()) {
            case R.id.edit_trans_img_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(0, 0) + ""));
                type_edittxt.setText("交通");
                this.type = "transportation";
                break;
            } case R.id.edit_food_img_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(1, 0) + ""));
                type_edittxt.setText("飲食");
                this.type = "food";
                break;
            } case R.id.edit_shopping_ing_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(2, 0) + ""));
                type_edittxt.setText("購物");
                this.type = "shopping";
                break;
            } case R.id.edit_home_img_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(3, 0) + ""));
                type_edittxt.setText("居家");
                this.type = "home";
                break;
            }  case R.id.edit_date_edittxt: {
                datePicker();
                break;
            } case R.id.edit_time_edittxt: {
                timePicker();
                break;
            } case R.id.edit_copy_btn: {
                try {
                    database.open(this.year, this.month);

                    this.cost = Integer.parseInt(cost_edittxt.getText().toString());
                    this.name = name_edittxt.getText().toString();
                    this.store = store_edittxt.getText().toString();

                    database.insert(this.year, this.month, this.day, this.time, this.type, this.name, this.store, this.cost);

                    Toast.makeText(EditItem.this, "複製成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(EditItem.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception err) {
                    Toast.makeText(EditItem.this, "複製失敗", Toast.LENGTH_SHORT).show();
                }
                break;
            } case R.id.edit_cancel_btn: {
                Intent intent = new Intent();
                intent.setClass(EditItem.this, MainActivity.class);
                startActivity(intent);
                break;
            } case R.id.edit_delete_btn: {
                new AlertDialog.Builder(EditItem.this)
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("確定要刪除嗎")
                        .setPositiveButton("刪除", (dialogInterface, i) -> {
                            try {
                                Toast.makeText(EditItem.this, "刪除成功", Toast.LENGTH_SHORT).show();
                                database.delete(this.id, this.year, this.month);
                            } catch (Exception err) {
                                Toast.makeText(EditItem.this, "刪除失敗", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent();
                            intent.setClass(EditItem.this, MainActivity.class);
                            startActivity(intent);
                        })
                        .setNegativeButton("取消", (dialogInterface, i) -> { })
                        .show();
                break;
            } case R.id.edit_update_btn: {
                try {
                    this.cost = Integer.parseInt(cost_edittxt.getText().toString());
                    this.name = name_edittxt.getText().toString();
                    this.store = store_edittxt.getText().toString();

                    if(this.year != this.pre_year || this.month != this.pre_month) {
                        database.delete(this.id, this.pre_year, this.pre_month);
                        database.open(year, month);
                        this.pre_year = this.year;
                        this.pre_month = this.month;
                        database.insert(this.year, this.month, this.day, this.time, this.type, this.name, this.store, this.cost);
                    } else {
                        database.update(this.id, this.year, this.month, this.day, this.time, this.type, this.name, this.store, this.cost);
                    }

                    Toast.makeText(EditItem.this, "編輯成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(EditItem.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception err) {
                    Toast.makeText(EditItem.this, "編輯失敗", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    };

    private void getItemInfo() {
        this.cursor = database.get(this.id, this.pre_year, this.pre_month);

        this.day = cursor.getInt(1);
        this.time = cursor.getInt(2);
        this.type = cursor.getString(3);
        this.name = cursor.getString(4);
        this.store = cursor.getString(5);
        this.cost = cursor.getInt(6);
        this.time_str = database.convertToTimeString(time);
    }

    @SuppressLint("ResourceType")
    private void setItem() {
        TypedArray icon_list = getResources().obtainTypedArray(R.array.icon_list);

        switch (this.type) {
            case "transportation": {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(0, 0) + ""));
                type_edittxt.setText("交通");
                break;
            } case "food": {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(1, 0) + ""));
                type_edittxt.setText("飲食");
                break;
            } case "shopping": {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(2, 0) + ""));
                type_edittxt.setText("購物");
                break;
            } case "home": {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(3, 0) + ""));
                type_edittxt.setText("居家");
                break;
            }
        }
        cost_edittxt.setText(this.cost + "");
        store_edittxt.setText(this.store);
        name_edittxt.setText(this.name);
        date_edittxt.setText(this.pre_year + "/" + this.pre_month + "/" + this.day);
        time_edittxt.setText(this.time_str);
    }

    public void datePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, m_year, m_month, m_day) -> {
                    year = m_year;
                    month = m_month + 1;
                    day = m_day;
                    date_edittxt.setText(m_year + "/" + (m_month + 1) + "/" + m_day);
                }, current_year, current_month, current_day);
        datePickerDialog.show();
    }

    public void timePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, m_hour, m_min) -> {
                    hour = m_hour;
                    min = m_min;
                    time = database.convertToMinInt(hour, min);
                    time_edittxt.setText(m_hour + ":" + m_min);
                }, current_hour, current_min, true);
        timePickerDialog.show();
    }
}
package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class AddItem extends AppCompatActivity {

    private db database = null;
    private Calendar calendar = Calendar.getInstance();
    ImageButton trans_img_btn, food_img_btn, shopping_img_btn, home_img_btn;
    ImageView type_img;
    EditText type_edittxt, cost_edittxt, store_edittxt, name_edittxt, date_edittxt, time_edittxt;
    Button cancel_btn, add_btn;

    private int current_year, current_month, current_day, current_hour, current_min;
    private int year, month, day, hour, min, time;
    private String type = "transportation";
    private String name = " ";
    private String store = " ";
    private int cost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        trans_img_btn = findViewById(R.id.add_trans_img_btn);
        food_img_btn = findViewById(R.id.add_food_img_btn);
        shopping_img_btn = findViewById(R.id.add_shopping_ing_btn);
        home_img_btn = findViewById(R.id.add_home_img_btn);
        type_img = findViewById(R.id.add_type_img);
        type_edittxt = findViewById(R.id.add_type_edittxt);
        cost_edittxt = findViewById(R.id.add_cost_edittxt);
        store_edittxt =findViewById(R.id.add_store_edittxt);
        name_edittxt = findViewById(R.id.add_name_edittxt);
        date_edittxt = findViewById(R.id.add_date_edittxt);
        time_edittxt = findViewById(R.id.add_time_edittxt);
        cancel_btn = findViewById(R.id.add_cancel_btn);
        add_btn = findViewById(R.id.add_add_btn);

        date_edittxt.setInputType(InputType.TYPE_NULL);
        time_edittxt.setInputType(InputType.TYPE_NULL);

        database = new db(this);

        this.current_year = calendar.get(Calendar.YEAR);
        this.current_month = calendar.get(Calendar.MONTH);
        this.current_day = calendar.get(Calendar.DAY_OF_MONTH);
        this.current_hour = calendar.get(Calendar.HOUR);
        this.current_min = calendar.get(Calendar.MINUTE);

        this.year = current_year;
        this.month = current_month;
        this.day = current_day;
        this.hour = current_hour;
        this.min = current_min;
        this.time = database.convertToMinInt(hour, min);

        database.open(year, month);

        trans_img_btn.setOnClickListener(onClickListener);
        food_img_btn.setOnClickListener(onClickListener);
        shopping_img_btn.setOnClickListener(onClickListener);
        home_img_btn.setOnClickListener(onClickListener);
        date_edittxt.setOnClickListener(onClickListener);
        time_edittxt.setOnClickListener(onClickListener);
        cancel_btn.setOnClickListener(onClickListener);
        add_btn.setOnClickListener(onClickListener);
    }

    @SuppressLint("ResourceType")
    View.OnClickListener onClickListener = view -> {
        TypedArray icon_list = getResources().obtainTypedArray(R.array.icon_list);

        switch (view.getId()) {
            case R.id.add_trans_img_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(0, 0) + ""));
                type_edittxt.setText("交通");
                this.type = "transportation";
                break;
            } case R.id.add_food_img_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(1, 0) + ""));
                type_edittxt.setText("飲食");
                this.type = "food";
                break;
            } case R.id.add_shopping_ing_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(2, 0) + ""));
                type_edittxt.setText("購物");
                this.type = "shopping";
                break;
            } case R.id.add_home_img_btn: {
                type_img.setImageResource(Integer.parseInt(icon_list.getResourceId(3, 0) + ""));
                type_edittxt.setText("居家");
                this.type = "home";
                break;
            } case R.id.add_date_edittxt: {
                datePicker();
                break;
            } case R.id.add_time_edittxt: {
                timePicker();
                break;
            } case R.id.add_cancel_btn: {
                Intent intent = new Intent();
                intent.setClass(AddItem.this, MainActivity.class);
                startActivity(intent);
                break;
            } case R.id.add_add_btn: {
                try {
                    this.cost = Integer.parseInt(cost_edittxt.getText().toString());
                    this.name = name_edittxt.getText().toString();
                    this.store = store_edittxt.getText().toString();
                    database.insert(year, month, day, time, type, name, store, cost);
                    Toast.makeText(AddItem.this, "添加成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(AddItem.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception err) {
                    Toast.makeText(AddItem.this, "添加失敗", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    };

    public void datePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, m_year, m_month, m_day) -> {
                    if(this.year != m_year || this.month != m_month) {
                        database.open(m_year, m_month);
                        this.year = m_year;
                        this.month = m_month + 1;
                    }
                    this.day = m_day;
                    date_edittxt.setText(m_year + "/" + (m_month + 1) + "/" + m_day);
                }, current_year, current_month, current_day);
        datePickerDialog.show();
    }

    public void timePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, m_hour, m_min) -> {
                    this.hour = m_hour;
                    this.min = m_min;
                    this.time = database.convertToMinInt(hour, min);
                    time_edittxt.setText(m_hour + ":" + m_min);
                }, current_hour, current_min, true);
        timePickerDialog.show();
    }
}
package com.example.samoney;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {

    EditText account_edittxt, password_edittxt;
    Button log_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        account_edittxt = findViewById(R.id.account_edittxt);
        password_edittxt = findViewById(R.id.password_edittxt);
        log_in_btn = findViewById(R.id.log_in_btn);

        log_in_btn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                String account_in = account_edittxt.getText().toString();
                String password_in = password_edittxt.getText().toString();

                if (account_in.equals(getString(R.string.account)) && password_in.equals(getString(R.string.password))) {
                    Toast toast = Toast.makeText(LogIn.this, "歡迎，北極狐！", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent();
                    intent.setClass(LogIn.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(LogIn.this, "帳號或密碼錯誤！", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (Exception err) {
                Toast toast = Toast.makeText(LogIn.this, "不確實登入的話就沒辦法用喔！", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };
}

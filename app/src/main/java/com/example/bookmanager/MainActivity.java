package com.example.bookmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        txtError = findViewById(R.id.txtError);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                txtError.setText("Vui lòng nhập tên đăng nhập và mật khẩu");
                txtError.setTextColor(getColor(android.R.color.holo_red_dark));
                txtError.setVisibility(View.VISIBLE);
            } else if (username.equals("admin") && password.equals("123456")) {
                Intent intent = new Intent(MainActivity.this, BookListActivity.class);
                startActivity(intent);
                finish();
            } else {
                txtError.setText("Sai tên đăng nhập hoặc mật khẩu");
                txtError.setTextColor(getColor(android.R.color.holo_red_dark));
                txtError.setVisibility(View.VISIBLE);
            }
        });
    }
}
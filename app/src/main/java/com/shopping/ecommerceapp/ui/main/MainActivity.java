package com.shopping.ecommerceapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import com.shopping.ecommerceapp.R;
import com.shopping.ecommerceapp.ui.login.LoginActivity;
import com.shopping.ecommerceapp.ui.signup.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    AppCompatButton haveAnAccBtn, joinNowBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        clicks();
    }

    private void clicks() {
        joinNowBtn.setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));
        haveAnAccBtn.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private void initView() {
        haveAnAccBtn = findViewById(R.id.have_acc_btn);
        joinNowBtn = findViewById(R.id.join_btn);
    }
}
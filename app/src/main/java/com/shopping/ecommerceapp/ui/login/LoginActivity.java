package com.shopping.ecommerceapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.shopping.ecommerceapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    CheckBox rememberMeCBox;
    TextView forgotPasswordTv, adminTv;
    AppCompatButton loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        rememberMeCBox = findViewById(R.id.remember_me_chbx);
        forgotPasswordTv = findViewById(R.id.forgot_pass_tv);
        adminTv = findViewById(R.id.admin_tv);
        loginBtn = findViewById(R.id.login_btn);
    }
}
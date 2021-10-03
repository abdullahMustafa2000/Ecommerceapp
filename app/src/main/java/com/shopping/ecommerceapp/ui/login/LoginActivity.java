package com.shopping.ecommerceapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.rey.material.widget.CheckBox;
import com.shopping.ecommerceapp.R;
import com.shopping.ecommerceapp.classes.Statics;
import com.shopping.ecommerceapp.modules.UserModule;
import com.shopping.ecommerceapp.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt, passwordEt;
    CheckBox rememberMeCBox;
    TextView forgotPasswordTv, adminTv;
    AppCompatButton loginBtn;
    ProgressDialog loadingBar;
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initViewModel();
        clicks();
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void clicks() {
        loginBtn.setOnClickListener(view -> userLogin());
    }

    private void userLogin() {
        String email, password;
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        if (inputIsOk(email, password)) {
            checkEmail(email, password);
            showLoadingBar();
        } else {
            showToast(getString(R.string.fill_all_feilds));
        }
    }

    private void showLoadingBar() {
        loadingBar.setTitle(R.string.login_loading);
        loadingBar.setMessage(getString(R.string.wait_message));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    private void insertUserToDB(UserModule userModule) {
        loginViewModel.putUserInDb(userModule);
        SharedPreferences preferences;
        preferences = getSharedPreferences(Statics.SHARED_PREF_NAME, MODE_PRIVATE);
        preferences.edit().putString(Statics.UID_SHARED_PREF, userModule.getUid()).apply();
    }

    private void checkEmail(String email, String password) {
        loginViewModel.userLogin(email, password).observe(this, this::onResult);
    }

    private boolean inputIsOk(String email, String password) {
        return (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        rememberMeCBox = findViewById(R.id.remember_me_chbx);
        forgotPasswordTv = findViewById(R.id.forgot_pass_tv);
        adminTv = findViewById(R.id.admin_tv);
        loginBtn = findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);
    }

    private void onResult(Pair<UserModule, String> pairResult) {
        loadingBar.dismiss();
        if (pairResult.first != null && pairResult.second == null) {
            if (rememberMeCBox.isChecked())
                insertUserToDB(pairResult.first);
            startActivity(intentData(pairResult.first));
            finish();
        } else if (pairResult.first == null && pairResult.second == null) {
            showToast(getString(R.string.user_not_found));
        } else if (pairResult.first == null && pairResult.second != null) {
            if (pairResult.second.contains("internet"))
                Toast.makeText(this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private Intent intentData(UserModule user) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Statics.INTENT_USER_NAME, user.getUsername());
        intent.putExtra(Statics.INTENT_EMAIL, user.getEmail());
        intent.putExtra(Statics.INTENT_PASSWORD, user.getPassword());
        intent.putExtra(Statics.INTENT_PHONE, user.getPhone());
        return intent;
    }
}
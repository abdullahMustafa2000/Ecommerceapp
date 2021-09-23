package com.shopping.ecommerceapp.ui.signup;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseError;
import com.rey.material.widget.CheckBox;
import com.shopping.ecommerceapp.R;
import com.shopping.ecommerceapp.ui.login.LoginActivity;
import com.shopping.ecommerceapp.ui.main.MainActivity;
import com.shopping.ecommerceapp.modules.UserModule;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameEt, emailEt, passwordEt, phoneEt;
    CheckBox rememberMeCb;
    AppCompatButton signUpBtn;
    ProgressDialog loadingBar;
    SignUpViewModel signUpViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        initViewModel();
        clicks();
    }

    private void initViewModel() {
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    private void clicks() {
        signUpBtn.setOnClickListener(view -> createAccount());
    }

    private void createAccount() {
        String username, email, password, phone;
        username = usernameEt.getText().toString();
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString();
        phone = phoneEt.getText().toString();
        if (inputIsOk(username, email, password)) {
            showProgressBar();
            validEmail(phone, username, email, password);
        } else {
            Toast.makeText(this, getString(R.string.fill_all_feilds), Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgressBar() {
        loadingBar.setTitle(R.string.creating_your_acc);
        loadingBar.setMessage(getString(R.string.wait_message));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    private boolean inputIsOk(String username, String email, String password) {
        return (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password));
    }

    private void validEmail(String phone, String username, String email, String password) {
        UserModule userModule = new UserModule(phone, username, email, password);
        signUpViewModel.createAccount(userModule).observe(this, this::onResult);
    }

    private void initView() {
        usernameEt = findViewById(R.id.name_et);
        passwordEt = findViewById(R.id.su_password_et);
        emailEt = findViewById(R.id.su_email_et);
        phoneEt = findViewById(R.id.phone_et);
        signUpBtn = findViewById(R.id.sign_up_btn);
        rememberMeCb = findViewById(R.id.su_remember_me_btn);
        loadingBar = new ProgressDialog(this);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startNewActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    private void onResult(Pair<Task<Void>, DatabaseError> resultPair) {
        if (resultPair.first != null && resultPair.second == null) {
            // task is successful (OnComplete)
            showToast(getString(R.string.email_created));
            loadingBar.dismiss();
            startNewActivity(LoginActivity.class);

        } else if (resultPair.first == null && resultPair.second == null) {
            // user phone is exists (isExists)
            showToast(getString(R.string.phone_is_exists));
            loadingBar.dismiss();
        } else if (resultPair.first == null && resultPair.second != null) {
            // error in network or anything else (OnCanceled)
            if (resultPair.second.getMessage().contains("internet")) {
                showToast(getString(R.string.check_internet));
            } else {
                showToast(getString(R.string.something_wrong));
            }
            startNewActivity(MainActivity.class);
            loadingBar.dismiss();
        }
    }
}
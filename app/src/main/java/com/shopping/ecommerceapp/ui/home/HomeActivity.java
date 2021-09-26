package com.shopping.ecommerceapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.shopping.ecommerceapp.R;
import com.shopping.ecommerceapp.classes.Statics;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        if (intent != null) {
            TextView welcomeTxt = findViewById(R.id.welcome_txt);
            String data = intent.getStringExtra(Statics.INTENT_EMAIL);
            welcomeTxt.setText(data);
        }
    }
}
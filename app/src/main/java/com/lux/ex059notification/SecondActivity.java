package com.lux.ex059notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //제목줄 제목 글씨 변경
        getSupportActionBar().setTitle("Second Activity");

    }
}
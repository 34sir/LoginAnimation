package com.example.chukc.loginanimation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import anim.CircularProgressButton;

public class MainActivity extends AppCompatActivity {
    CircularProgressButton circularButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularButton = (CircularProgressButton) findViewById(R.id.circularButton);
        circularButton.setIndeterminateProgressMode(true); //设置为true才会出现类似progress过程
        circularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularButton.setProgress(50);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        circularButton.setProgress(0);
                    }
                },3000);
            }
        });
    }
}

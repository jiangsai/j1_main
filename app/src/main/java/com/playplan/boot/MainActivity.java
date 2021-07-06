package com.playplan.boot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.architecture_view.Test;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test.test();
    }
}
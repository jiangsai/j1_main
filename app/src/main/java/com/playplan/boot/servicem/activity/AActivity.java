package com.playplan.boot.servicem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.boot.R;
import com.playplan.boot.servicem.service.MySelfService;

public class AActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aactivity);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startService(new Intent(AActivity.this, MySelfService.class));
                startActivity(new Intent(AActivity.this, BActivity.class));
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(AActivity.this, MySelfService.class));
            }
        });
    }
}
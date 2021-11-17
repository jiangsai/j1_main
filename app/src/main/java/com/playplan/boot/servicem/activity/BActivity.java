package com.playplan.boot.servicem.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.boot.R;
import com.playplan.boot.servicem.service.MySelfService;

public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bactivity);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jyt", "click");
                startService(new Intent(BActivity.this, MySelfService.class));

                Intent intent = new Intent(BActivity.this, MySelfService.class);
                intent.setType("1");
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });

    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("jyt", "onServiceConnected b");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("jyt", "onServiceDisconnected b");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
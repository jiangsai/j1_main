package com.playplan.boot.Jump;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.toast.ToastUtils;
import com.playplan.boot.databinding.ActivityTwoBinding;
import com.playplan.boot.process.IMyAidlInterface;

public class TwoActivity extends AppCompatActivity {
    private ActivityTwoBinding binding;
    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setResult(0, new Intent().putExtra("111", "x"));
//                finish();
                try {
                    ToastUtils.show(iMyAidlInterface.getString());
                    iMyAidlInterface.setData();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent = new Intent();
        intent.setAction("com.playplan.boot.process");
        intent.setPackage("com.playplan.boot");

        ComponentName componentName = new ComponentName(getPackageName(), "com.playplan.boot.process.MyService");
        intent.setComponent(componentName);

        bindService(intent, new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
        startService(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
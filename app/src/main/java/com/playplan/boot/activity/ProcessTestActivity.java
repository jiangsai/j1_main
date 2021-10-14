package com.playplan.boot.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.toast.ToastUtils;
import com.playplan.boot.databinding.ActivityProcessTestBinding;
import com.playplan.boot.process.IMyAidlInterface;
import com.playplan.boot.process.IMyCallBack;
import com.playplan.boot.process.MyData;


public class ProcessTestActivity extends AppCompatActivity {
    private ActivityProcessTestBinding binding;
    private IMyAidlInterface iMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProcessTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setResult(0, new Intent().putExtra("111", "x"));
//                finish();
                try {
                    ToastUtils.show(iMyAidlInterface.getString());
                    iMyAidlInterface.setData(new MyData("666"));
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
                try {
                    iMyAidlInterface.setListener(stub);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
        startService(intent);

    }

    IMyCallBack.Stub stub = new IMyCallBack.Stub() {
        @Override
        public void callData() {
            Log.e("jyt", "callData");
        }
    };
}
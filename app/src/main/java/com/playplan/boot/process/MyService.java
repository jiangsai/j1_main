package com.playplan.boot.process;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * author : jyt
 * time   : 2021/10/13
 * desc   :
 */
public class MyService extends Service {
    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        Log.e("jyt", "service oncreate " + pid);

        mHandle.sendMessage(Message.obtain());

    }

    Handler mHandle = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mHandle.sendMessageDelayed(Message.obtain(), 2000);
            Log.e("jyt", "=" + (myCallBack == null));
            if (myCallBack != null) {
                try {
                    myCallBack.callData();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public IMyCallBack myCallBack = null;

    class MyBinder extends IMyAidlInterface.Stub {


        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getString() {
            return "hahaha";
        }

        @Override
        public void setListener(IMyCallBack callback) {
            Log.e("jyt", "setData" + (callback == null));
            myCallBack = callback;
            Log.e("jyt", "=" + (myCallBack == null));
        }

        @Override
        public void setData(MyData data) throws RemoteException {
            Log.e("jyt", "mydata=" + data.name);
        }

        public void setData() {
            int pid = android.os.Process.myPid();
            Log.e("jyt", "process" + pid);
        }
    }
}

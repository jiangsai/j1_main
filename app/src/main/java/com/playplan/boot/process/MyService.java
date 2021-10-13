package com.playplan.boot.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

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
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends IMyAidlInterface.Stub {


        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getString() throws RemoteException {
            return "hahaha";
        }

        public void setData() {
            int pid = android.os.Process.myPid();
            Log.e("jyt", "process" + pid);
        }
    }
}

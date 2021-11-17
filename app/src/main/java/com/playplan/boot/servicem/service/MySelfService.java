package com.playplan.boot.servicem.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * author : jyt
 * time   : 2021/11/17
 * desc   :
 */
public class MySelfService extends Service {

    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public MySelfService getService() {
            return MySelfService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("jyt", "onBind");
        return new MsgBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("jyt", "onUnbind");
        return super.onUnbind(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("jyt", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("jyt", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("jyt", "onDestroy");
    }
}

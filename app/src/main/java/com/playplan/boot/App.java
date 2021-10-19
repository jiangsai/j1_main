package com.playplan.boot;

import android.app.Application;

import com.hjq.toast.ToastUtils;


/**
 * author : jyt
 * time   : 2021/10/13
 * desc   :
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }
}

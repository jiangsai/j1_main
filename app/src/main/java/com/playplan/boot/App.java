package com.playplan.boot;

import android.app.Application;
import android.util.Log;

import com.hjq.toast.ToastUtils;
import com.playplan.fastmodules.module.IFastModule;
import com.playplan.fastmodules.module.ModuleLoader;


/**
 * author : jyt
 * time   : 2021/10/13
 * desc   :
 */
public class App extends Application implements IFastModule {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        ModuleLoader.initMoudle(SystemUtil.getCurProcessName(), SystemUtil.isProcess(BuildConfig.APPLICATION_ID));
    }

    @Override
    public void intitFastNodeList() {
        synchronized (this) {
            Log.e("jyt", "test === initFastNodeList");
        }

    }


}

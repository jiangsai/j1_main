package com.playplan.boot.utils;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Properties;


/**
 * User: hqs
 * Date: 2016/3/18
 * Time: 15:45
 * 用户设备相关信息
 */
public class DeviceUtil {

    public static String deviceId(Context context) {
        try {
            String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return Build.SERIAL + androidId;
        } catch (Exception e) {
            return "000000000000000";
        }
    }

//    public static String ua() {
//        return "LiveApp/" + BuildConfig.VERSION_NAME + " Android/" + Build.VERSION.RELEASE + " (" + Build.MODEL + ")";
//    }
//
//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     *
//     * @param dpValue dp值
//     * @return 转换后的px值
//     */
//    public static int dp2px(float dpValue) {
//        final float scale = AppHolder.getInstance().getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    /**
//     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//     *
//     * @param pxValue px值
//     * @return 转换后的dp值
//     */
//    public static int px2dp(float pxValue) {
//        final float scale = AppHolder.getInstance().getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//    public static int sp2px(float spValue) {
//        final float fontScale = AppHolder.getInstance().getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }

    /**
     * 获取屏幕分辨宽，以px为单位
     *
     * @param context 上下文
     * @return 屏幕宽
     */
    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕分辨高，以px为单位
     *
     * @param context 上下文
     * @return 屏幕高
     */
    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getHeightByDp(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取底部导航栏高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    public static boolean isMiuiV5() {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            inputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            properties.load(inputStream);
            String version = properties.getProperty("ro.miui.ui.version.name", null);
            if (version != null) {
                if ("V5".equals(version)) {
                    return true;
                }
            }
            return false;
        } catch (final IOException e) {
            return false;
        } finally {
            //   IOUtil.close(inputStream);
        }
    }

    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 获取屏幕物理尺寸，保留小数后一位，单位英寸
     *
     * @param context
     * @return
     */
    public static double getScreenInch(Context context) {
        double mInch = 0.0d;

        try {
            int realWidth, realHeight;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (Build.VERSION.SDK_INT < 17 && Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            BigDecimal bd = new BigDecimal(Math.sqrt((realWidth / metrics.xdpi) * (realWidth / metrics.xdpi) + (realHeight / metrics.ydpi) * (realHeight / metrics.ydpi)));
            mInch = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mInch;
    }

    public static int getCpuNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获得系统总内存，单位是GB
     *
     * @return
     */
    public static float getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String memoryOfString = "";
        long initialMemory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            if (str2 != null && !"".equals(str2)) {
                for (int i = 0; i < str2.length(); i++) {
                    char c = str2.charAt(i);
                    if (c >= 48 && c <= 57) {
                        memoryOfString += c;
                    }
                }
            }
            initialMemory = Integer.parseInt(memoryOfString);
            localBufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return initialMemory / (1024 * 1024.0f);
    }

    /**
     * 获取当前手机可用内存大小，单位MB
     *
     * @param context
     * @return
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);
    }

    public static boolean isHighEndPhone(Context context) {
        int cpu = getCpuNum();
        float memory = getTotalMemory();
        long availableMemory = getAvailableMemory(context);
        float dpi = getDensity(context);
        return cpu >= 4 && memory >= 1.8 && availableMemory >= 200 && dpi >= 3.0;
    }

    public static void translucentStatusBar(Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int option = decorView.getSystemUiVisibility();
            option |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //设置状态栏字体为黑色
    public static void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isMIUI()) {
                setMiuiStatusBarDarkMode(activity, true);
            }
            View decorView = activity.getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility();
            option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
        }
    }

    public static boolean isMIUI() {
//        String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
//        String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
//        String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
//        try {
//            final BuildProperties prop = BuildProperties.newInstance();
//            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
//                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
//        } catch (final IOException e) {
//            return false;
//        }
        return true;
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setWhiteStateBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isMIUI()) {
                setMiuiStatusBarDarkMode(activity, true);
            }
            activity.getWindow().setStatusBarColor(Color.WHITE);
            View decorView = activity.getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility();
            option |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
        }
    }

    public static int checkDeviceHasNavigationBar(Context context) {

        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }

    //判断navigationbar是否显示
    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !(menu || back);
        }
    }

    //获取手机屏幕真实高度
    public static int getRealHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            display.getSize(size);
        }
        return size.y;
    }

    public static boolean copyText(Context context, String text) {
        if (context == null || text == null) {
            return false;
        }
        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text", text);
        if (manager != null) {
            manager.setPrimaryClip(data);
            return true;
        }
        return false;
    }


    public static boolean isHTC_D10w() {
        return "HTC D10w".equals(Build.MODEL);
    }

    public static boolean isGoogle() {
        return "google".equals(Build.BRAND);
    }


    @SuppressLint("MissingPermission")
    public static String getImeiData(Context context) {
        if (context == null) {
            return "";
        }
        //获取IMEI码
        TelephonyManager telephonyManager1 = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (telephonyManager1 != null) {
                String imei = telephonyManager1.getDeviceId();
                if (!TextUtils.isEmpty(imei)) {
                    return imei;
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static void setTranslucentStatus(Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int option = decorView.getSystemUiVisibility();
            option |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}

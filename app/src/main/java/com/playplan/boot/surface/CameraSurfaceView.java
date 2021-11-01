package com.playplan.boot.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.io.IOException;

/**
 * author : jyt
 * time   : 2021/10/26
 * desc   :
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    SurfaceHolder mHolder;

    Canvas mCanvas;

    boolean isOk;


    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    boolean s = false;
    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
//        setFocusableInTouchMode(true);
//        this.setKeepScreenOn(true);

        //如果要显示在surface上，就要加这些
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                ViewGroup pareView = (ViewGroup) getParent();
                pareView.removeView(CameraSurfaceView.this);
                if (!s) {
                    s = true;
                    // mHolder.setKeepScreenOn(true);
                    mHolder.setFormat(PixelFormat.TRANSPARENT);
                    setZOrderOnTop(true);
                    setZOrderMediaOverlay(true);

                } else {
                    s = false;
                    //   mHolder.setKeepScreenOn(false);
                    mHolder.setFormat(PixelFormat.TRANSPARENT);
                    setZOrderOnTop(false);
                    setZOrderMediaOverlay(false);
                }
                pareView.addView(CameraSurfaceView.this);
                setVisibility(VISIBLE);
                Log.e("jyt-Camera", "====" + s);
            }
        });

    }

    Camera mCamera;

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        isOk = true;
        // new Thread(this).start();
        Log.e("jyt", "surfaceCreated");
        mCamera = Camera.open(0);
        // Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        setLayoutParams(new FrameLayout.LayoutParams(
//                previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException t) {
            Log.e("jyt", t.getMessage());
        }
        mCamera.startPreview();
        //   setAlpha(0.5f);
        //   setRotation(90.0f);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.e("jyt", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        isOk = false;
        Log.e("jyt", "surfaceDestroyed");
    }

    @Override
    public void run() {

        while (isOk) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            draw();
        }

    }


    public void draw() {
        try {
            //锁定画布并返回画布对象
            mCanvas = mHolder.lockCanvas();
            //接下去就是在画布上进行一下draw
            mCanvas.drawColor(Color.parseColor("#4f4f4f"));
        } catch (Exception e) {
        } finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}

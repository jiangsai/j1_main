package com.playplan.boot.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * author : jyt
 * time   : 2021/10/27
 * desc   :
 */
public class EmptySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    SurfaceHolder mHolder;
    Canvas mCanvas;
    boolean isOk;

    public EmptySurfaceView(Context context) {
        this(context, null);
    }

    public EmptySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        isOk = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        isOk = false;
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
            mCanvas.drawColor(Color.parseColor("#00000000"));
        } catch (Exception e) {
        } finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}

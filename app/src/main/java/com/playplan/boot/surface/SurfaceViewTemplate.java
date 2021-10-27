package com.playplan.boot.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * author : jyt
 * time   : 2021/10/26
 * desc   :
 */
public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    Paint mPaint;

    SurfaceHolder mHolder;

    //用于绘图的Canvas
    private Canvas mCanvas;

    //路径
    private Path mPath;

    String TAG = "jyt";

    public SurfaceViewTemplate(Context context) {
        this(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPath = new Path();
    }

    boolean isOk;

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
            draw();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent: down");
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent: move");
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent: up");
                break;
        }
        return true;
    }


    private void draw() {
        try {
            Thread.sleep(10);
            //锁定画布并返回画布对象
            mCanvas = mHolder.lockCanvas();
            //接下去就是在画布上进行一下draw
            mCanvas.drawColor(Color.parseColor("#000000"));
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
        } finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    /**
     * 清屏
     *
     * @return true
     */
    public boolean reDraw() {
        mPath.reset();
        return true;
    }
}

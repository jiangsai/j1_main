package com.playplan.boot.surface;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * author : jyt
 * time   : 2021/10/26
 * desc   :
 */
public class MyTextrueView extends TextureView implements TextureView.SurfaceTextureListener {
    public MyTextrueView(@NonNull Context context) {
        this(context, null);
    }

    public MyTextrueView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextrueView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setSurfaceTextureListener(this);
    }

    Camera mCamera;

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        Log.e("jyt", "onSurfaceTextureAvailable");
        mCamera = Camera.open();
        //   Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
//        setLayoutParams(new FrameLayout.LayoutParams(
//                previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surface);
        } catch (IOException t) {
            Log.e("jyt", t.getMessage());
        }
        mCamera.startPreview();
//        setAlpha(0.5f);
        setRotation(90.0f);
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        Log.e("jyt", "onSurfaceTextureDestroyed");
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }
}

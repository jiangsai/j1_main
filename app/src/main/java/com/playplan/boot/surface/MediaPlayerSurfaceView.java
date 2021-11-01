package com.playplan.boot.surface;

import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
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
 * time   : 2021/10/27
 * desc   :
 */
public class MediaPlayerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    private MediaPlayer mediaPlayer;

    public MediaPlayerSurfaceView(Context context) {
        this(context, null);
    }


    public MediaPlayerSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MediaPlayerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    boolean s = false;

    private void initView() {
        mHolder = getHolder();

        mHolder.addCallback(this);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    setDataPath("https://media.w3.org/2010/05/sintel/trailer.mp4");
                } else {
                    setVisibility(GONE);
                    ViewGroup pareView = (ViewGroup) getParent();
                    pareView.removeView(MediaPlayerSurfaceView.this);
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
                    pareView.addView(MediaPlayerSurfaceView.this);
                    setVisibility(VISIBLE);
                    Log.e("jyt-MediaPlayer", "====" + s);
                }

            }
        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaPlayer.setDisplay(mHolder);
        // setDataPath("https://media.w3.org/2010/05/sintel/trailer.mp4");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
//        if (mediaPlayer!=null){
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
    }

    public void setDataPath(String path) {
        Log.e("jyt", "set");
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                Log.e("jyt", "play shipin");
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("jyt", "play fail=" + what + "==" + extra);
                    return false;
                }
            });
        } catch (IOException e) {
            Log.e("jyt", "play " + e.getMessage());
            e.printStackTrace();
        }
    }
}

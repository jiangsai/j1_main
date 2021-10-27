package com.playplan.boot.surface;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.playplan.boot.databinding.ViewMoveBinding;
import com.playplan.boot.utils.DeviceUtil;

/**
 * author : jyt
 * time   : 2021/10/26
 * desc   :
 */
public class MoveView extends FrameLayout {

    private GestureDetector gestureDetector; //手势检测
    private VelocityTracker mVelocityTracker; //手指滑动速速检测

    private float lastX, lastY; //手指上次滚动的坐标
    private float downX, downY; //手指按下时的坐标

    private OverScroller mFlingScroller; //自动滑动辅助器

    private int mMinVelocity, mMaxVelocity; //最小、最大滑动速度

    private int mScreenWidth, mScreenHeight;

    private boolean isFling;//是否正在滑动

    private int mWidth, mHeight; //自身宽高


    public MoveView(@NonNull Context context) {
        this(context, null);
    }

    public MoveView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewMoveBinding binding = ViewMoveBinding.inflate(LayoutInflater.from(context));
        addView(binding.getRoot());


        gestureDetector = new GestureDetector(context, onGestureListener);
        mVelocityTracker = VelocityTracker.obtain();

        mMinVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();

        DecelerateInterpolator di = new DecelerateInterpolator();
        mFlingScroller = new OverScroller(context, di);

        mScreenWidth = DeviceUtil.getWidth(context);
        mScreenHeight = DeviceUtil.getHeight(context);

        binding.tvPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // binding.mpSurfaceView.setDataPath("https://media.w3.org/2010/05/sintel/trailer.mp4");
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        boolean actionUp = event.getAction() == MotionEvent.ACTION_UP;
        if (!gestureDetector.onTouchEvent(event) && actionUp) {
            mVelocityTracker.clear();
        }
        return true;
    }


    private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            mVelocityTracker.addMovement(e);
            downX = lastX = e.getX();
            downY = lastY = e.getY();
            if (!mFlingScroller.isFinished()) {
                mFlingScroller.abortAnimation();
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            performClick();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            e2.offsetLocation(lastX - downX, lastY - downY);
            mVelocityTracker.addMovement(e2);
            float currX = e2.getX();
            float currY = e2.getY();
            distanceX = currX - lastX;
            distanceY = currY - lastY;
            translation(distanceX, distanceY);
            lastX = currX;
            lastY = currY;
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
            velocityX = mVelocityTracker.getXVelocity();
            velocityY = mVelocityTracker.getYVelocity();
            mVelocityTracker.clear();
            startFling(velocityX, velocityY);
            return true;
        }
    };

    private void translation(float translationX, float translationY) {
        setTranslationX(getTranslationX() + translationX);
        setTranslationY(getTranslationY() + translationY);
    }

    private void startFling(float xVelocity, float yVelocity) {
        if (Math.abs(xVelocity) < mMinVelocity && Math.abs(yVelocity) < mMinVelocity) {
            return;
        }

        int[] location = new int[2];
        getLocationOnScreen(location);
        int startX = location[0];
        int startY = location[1];

        lastX = startX;
        lastY = startY;
        int minX = -mWidth;
        int maxX = mScreenWidth;

        int minY = -mHeight;
        int maxY = mScreenHeight;
        isFling = true;
        mFlingScroller.fling(startX, startY, (int) xVelocity, (int) yVelocity, minX, maxX, minY, maxY);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (!isFling) {
            return;
        }
        if (mFlingScroller.computeScrollOffset()) {
            int currX = mFlingScroller.getCurrX();
            int currY = mFlingScroller.getCurrY();
            translation(currX - lastX, currY - lastY);
            lastX = currX;
            lastY = currY;
            postInvalidate();
        } else {
            isFling = false;
            int[] location = new int[2];
            getLocationOnScreen(location);
            if (location[0] <= -mWidth || location[0] >= mScreenWidth
                    || location[1] <= -mHeight || location[1] >= mScreenHeight) {
//                outScreen = true;
//                if (onOutScreenListener != null) {
//                    onOutScreenListener.outScreen();
//                }
//                //滑出屏幕，完全不可见
//                stop();
                setVisibility(INVISIBLE);
            }
        }
    }


}

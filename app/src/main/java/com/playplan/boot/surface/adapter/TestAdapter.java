package com.playplan.boot.surface.adapter;

import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.playplan.boot.R;
import com.playplan.boot.surface.MediaPlayerSurfaceView;
import com.playplan.boot.surface.bean.Test;

import java.util.List;

/**
 * author : jyt
 * time   : 2021/10/27
 * desc   :
 */
public class TestAdapter extends BaseQuickAdapter<Test, BaseViewHolder> {

    public TestAdapter(List<Test> data) {
        super(R.layout.item_test, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Test test) {
        FrameLayout fl = baseViewHolder.getView(R.id.fl);
        if (baseViewHolder.getAdapterPosition() == 5) {
            if (fl.getChildCount() == 0) {
                fl.addView(new MediaPlayerSurfaceView(getContext()));
            }

        } else {
            fl.removeAllViews();
        }
    }
}

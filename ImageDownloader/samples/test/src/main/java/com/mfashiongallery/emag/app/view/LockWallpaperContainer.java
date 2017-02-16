package com.mfashiongallery.emag.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import tool.imagedownloader.test.R;

/**
 * Created by liuchonghui on 2017/2/16.
 */
public class LockWallpaperContainer extends FrameLayout {
    public LockWallpaperContainer(Context context) {
        super(context);
    }

    public LockWallpaperContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockWallpaperContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LockWallpaperSwipeView swipeView = (LockWallpaperSwipeView)
                findViewById(R.id.swipe);
        if (swipeView != null ) {
            swipeView.setActionMenus();
        }
    }
}

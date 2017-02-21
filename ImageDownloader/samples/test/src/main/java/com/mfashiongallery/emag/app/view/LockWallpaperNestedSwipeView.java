package com.mfashiongallery.emag.app.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import tool.imagedownloader.test.R;

/**
 * Created by liuchonghui on 2017/2/16.
 */
public class LockWallpaperNestedSwipeView extends LockWallpaperSwipeView implements NestedScrollingParent {
    public LockWallpaperNestedSwipeView(Context context) {
        super(context);
        init();
    }

    public LockWallpaperNestedSwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LockWallpaperNestedSwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    View recycler;
    boolean onScrolledToTop;
    boolean onScrolledToBottom;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.addOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledUp() {
                Log.d("PPP", "onScrolledUp");
                onScrolledToTop = false;
                onScrolledToBottom = false;
            }

            @Override
            public void onScrolledDown() {
                Log.d("PPP", "onScrolledDown");
                onScrolledToTop = false;
                onScrolledToBottom = false;
            }

            @Override
            public void onScrolledToTop() {
                Log.d("PPP", "onScrolledToTop");
                onScrolledToTop = true;
                onScrolledToBottom = false;
            }

            @Override
            public void onScrolledToBottom() {
                Log.d("PPP", "onScrolledToBottom");
                onScrolledToBottom = true;
                onScrolledToTop = false;
            }
        });
    }

    final String Tag = "PPP";

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i(Tag, "onStartNestedScroll--" + "child:" + child + ",target:" + target + ",nestedScrollAxes:" + nestedScrollAxes);
        return true;
    }

    NestedScrollingParentHelper mParentHelper;
    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);

    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        Log.i(Tag, "onNestedScrollAccepted" + "child:" + child + ",target:" + target + ",nestedScrollAxes:" + nestedScrollAxes);
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i(Tag, "onStopNestedScroll--target:" + target);
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(Tag, "onNestedScroll--" + "target:" + target + ",dxConsumed" + dxConsumed + ",dyConsumed:" + dyConsumed
                + ",dxUnconsumed:" + dxUnconsumed + ",dyUnconsumed:" + dyUnconsumed);
    }

    boolean handleUp(int dy, int offsetY) {
        if (dy > 0) {
            if (getScrollY() < 170) {
                return true;
            }
        }
        return false;
    }

    boolean handleDw(int dy, int offsetY) {
        if (dy < 0) {
            if (getScrollY() > 0  && onScrolledToTop) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int[] offsetInWindow = new int[2];
        target.getLocationInWindow(offsetInWindow);
        if (handleUp(dy, offsetInWindow[1])) {
            scrollBy(0, dy);
            consumed[1] = dy;
        } else if (handleDw(dy, offsetInWindow[1])) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
//        if(showImg(dy)||hideImg(dy)){//如果父亲自己要滑动，则拦截
//            consumed[1]=dy;
//            scrollBy(0,dy);
//            Log.i("onNestedPreScroll","Parent滑动："+dy);
//        }
        Log.i(Tag, "onNestedPreScroll--getScrollY():" + getScrollY() + ",dx:" + dx + ",dy:" + dy + ",consumed:" + (consumed[0] != 0 || consumed[1] != 0) + ", offsetY = " + offsetInWindow[1]);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(Tag, "onNestedFling---------------------consume=" + consumed);
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i(Tag, "onNestedPreFling--target:" + target);
        if (onScrolledToTop || onScrolledToBottom) {
            return true;
        }
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        Log.i(Tag, "getNestedScrollAxes");
        return 0;
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y<0){
            y=0;
        }
        if(y>170){
            y=170;
        }
        super.scrollTo(x, y);
    }

    /**
     下拉的时候是否要向下滑动显示图片
     */
    public boolean showImg(int dy){
//        if(dy<0){
//            if(getScrollY()>0&&nsc.getScrollY()==0){//如果parent外框，还可以往上滑动
//                return true;
//            }
//        }


        return false;
    }

    /**
     * 上拉的时候，是否要向上滑动，隐藏图片
     * @return
     */
    public boolean hideImg(int dy){
//        if(dy>0){
//            if(getScrollY()<imgHeight){//如果parent外框，还可以往下滑动
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("aaa","getY():getRawY:"+event.getRawY());
        return super.dispatchTouchEvent(event);
    }
}

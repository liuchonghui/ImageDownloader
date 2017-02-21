package com.mfashiongallery.emag.app.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by liuchonghui on 2017/2/16.
 */
public abstract class OnVerticalScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        } else if (dy < 0) {
            onScrolledUp();
        } else if (dy > 0) {
            onScrolledDown();
        }
    }

    public abstract void onScrolledUp();

    public abstract void onScrolledDown();

    public abstract void onScrolledToTop();

    public abstract void onScrolledToBottom();
}
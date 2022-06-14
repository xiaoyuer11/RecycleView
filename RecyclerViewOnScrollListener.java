package com.example.recyclerview;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    private UpPullOnScrollListener listener;

    public RecyclerViewOnScrollListener(UpPullOnScrollListener listener) {
        this.listener = listener;
    }

    boolean isUpScroll = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            System.out.println("SCROLL_STATE_IDLE");
            int itemCount = manager.getItemCount();
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            if (lastItemPosition == (itemCount - 1) && isUpScroll) {
                listener.onLoadMore();
            }
            int firstItemPosition = manager.findFirstCompletelyVisibleItemPosition();
            if (firstItemPosition == (0) && !isUpScroll) {
                listener.onRefresh();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isUpScroll = dy > 0;
        System.out.println("onScrolled");
    }

    public interface UpPullOnScrollListener {
        void onLoadMore();

        void onRefresh();
    }
}

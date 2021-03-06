package com.jarvanmo.marsboot.widget.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mo on 17-8-28.
 *
 * @author mo
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;

    public boolean isLoading = false;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);


    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        if (lastVisibleItemPosition + 1 == layoutManager.getItemCount()) {
            if (!isLoading) {
                isLoading = true;
                onLoadMore();
            }
        }
    }

    public abstract void onLoadMore();

}

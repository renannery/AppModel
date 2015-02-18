package com.origamitecnologia.appmodel.control;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerView extends RecyclerView.OnScrollListener {
    private static int THRESHOLD_LIMIT = 5;

    private boolean loading = true;

    private int previousTotal = 0;
    private int visibleThreshold = THRESHOLD_LIMIT;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = recyclerView.getLayoutManager().getItemCount();
        firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoaded(totalItemCount);
            loading = true;
        }
    }

    public abstract void onLoaded(int offset);
}

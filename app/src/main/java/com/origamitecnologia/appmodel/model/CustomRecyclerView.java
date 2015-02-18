package com.origamitecnologia.appmodel.model;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by pokawa on 18/12/14.
 */
public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void defineLayoutManager(Context context) {
        RecyclerView.LayoutManager layoutManager;
        Configuration configuration = context.getResources().getConfiguration();

        int screenSize = (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);

        if((screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) || (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)) {
            layoutManager = new GridLayoutManager(context, 2);
            setLayoutManager(layoutManager);
        } else {
            layoutManager = new LinearLayoutManager(context);

            setLayoutManager(layoutManager);
        }
    }
}

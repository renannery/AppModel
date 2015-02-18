package com.origamitecnologia.appmodel.control;

import android.content.Context;
import android.content.res.Configuration;

public class DisplayCentral {
    private static DisplayCentral instance;

    private DisplayCentral() {

    }

    public static DisplayCentral getInstance() {
        if(instance == null) {
            instance = new DisplayCentral();
        }
        return instance;
    }

    public boolean isScreenSmall(Context context) {
        return ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL);
    }

    public boolean isScreenNormal(Context context) {
        return ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL);
    }

    public boolean isScreenLarge(Context context) {
        return ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public boolean isScreenExtraLarge(Context context) {
        return ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
    }

    public boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

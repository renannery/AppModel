package com.origamitecnologia.appmodel.control;

import android.content.Context;

import com.origamitecnologia.appmodel.R;

public class ServerCentral {
    private static ServerCentral instance;

    private ServerCentral() {

    }

    public static ServerCentral getInstance() {
        if(instance == null) {
            instance = new ServerCentral();
        }
        return instance;
    }

    public String getImageWithPath(Context context, String file) {
        return context.getResources().getString(R.string.image_server_path).concat(file);
    }
}

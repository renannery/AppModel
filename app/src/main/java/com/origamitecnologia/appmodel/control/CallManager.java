package com.origamitecnologia.appmodel.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.origamitecnologia.appmodel.model.User;
import com.origamitecnologia.appmodel.view.ActivityLogin;
import com.origamitecnologia.appmodel.view.ActivityMain;
import com.origamitecnologia.appmodel.view.ActivityProfile;
import com.origamitecnologia.appmodel.view.BaseActivity;
import com.origamitecnologia.appmodel.view.FragmentCreateNotification;
import com.origamitecnologia.appmodel.view.FragmentNotification;
import com.origamitecnologia.appmodel.view.FragmentTimeline;

public class CallManager {

    private static SharedPreferences sharedPreferences;

    public static final Intent login(Context context) {
        sharedPreferences = context.getSharedPreferences(ActivityLogin.USER_LOGED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(ActivityLogin.USER_LOGED_ID, ActivityLogin.USER_LOGED_NO_USER).putBoolean(ActivityLogin.USER_LOGED_VALIDATION, false).commit();
        Intent intent = new Intent(context, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static final Intent main(Context context) {
        sharedPreferences = context.getSharedPreferences(ActivityLogin.USER_LOGED_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(ActivityLogin.USER_LOGED_ID, ActivityLogin.USER_LOGED_NO_USER_STRING);
        User user = UserValidator.getInstance().getUserByUsername(context, username);
        SessionManager.getInstance().setCurrentUser(user);
        return new Intent(context, ActivityMain.class);
    }

    public static final Intent profile(Context context) {
        return new Intent(context, ActivityProfile.class);
    }

    public static final Fragment fragmentTimeline() {
        Fragment fragment = new FragmentTimeline();
        return fragment;
    }

    public static final Fragment fragmentCreateNotification() {
        Fragment fragment = new FragmentCreateNotification();
        return fragment;
    }

    public static final Fragment fragmentNotification(long id) {
        Fragment fragment = new FragmentNotification();
        Bundle bundle = new Bundle();
        bundle.putLong(FragmentNotification.BUNDLE_NOTIFICATION_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }
}

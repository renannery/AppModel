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

    private static CallManager instance;
    private BaseActivity activity;
    private SharedPreferences sharedPreferences;

    private CallManager() {

    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    public static CallManager getInstance() {
        if(instance == null) {
            instance = new CallManager();
        }
        return instance;
    }

    public void activityLogin() {
        sharedPreferences = activity.getSharedPreferences(ActivityLogin.USER_LOGED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(ActivityLogin.USER_LOGED_ID, ActivityLogin.USER_LOGED_NO_USER).putBoolean(ActivityLogin.USER_LOGED_VALIDATION, false).commit();
        Intent intent = new Intent(activity, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void activityMain() {
        sharedPreferences = activity.getSharedPreferences(ActivityLogin.USER_LOGED_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(ActivityLogin.USER_LOGED_ID, ActivityLogin.USER_LOGED_NO_USER_STRING);
        User user = UserValidator.getInstance().getUserByUsername(activity.getApplication(), username);
        SessionManager.getInstance().setCurrentUser(user);
        activity.startActivity(new Intent(activity, ActivityMain.class));
    }

    public void activityProfile() {
        activity.startActivity(new Intent(activity, ActivityProfile.class));
    }

    public void fragmentTimeline() {
        activity.setFragment(new FragmentTimeline(), false);
    }

    public void fragmentCreateNotification() {
        activity.setFragment(new FragmentCreateNotification(), true);
    }

    public void fragmentNotification(long id) {
        Fragment fragment = new FragmentNotification();
        Bundle bundle = new Bundle();
        bundle.putLong(FragmentNotification.BUNDLE_NOTIFICATION_ID, id);
        fragment.setArguments(bundle);
        activity.setFragment(fragment, true);
    }
}

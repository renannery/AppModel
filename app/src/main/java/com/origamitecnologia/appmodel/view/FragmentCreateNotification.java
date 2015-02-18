package com.origamitecnologia.appmodel.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.AnimationUtils;
import com.squareup.timessquare.CalendarPickerView;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pokawa on 16/12/14.
 */
public class FragmentCreateNotification extends BaseFragment {

    private static final String TAG = "fragmentTag";
    @InjectView(R.id.llCreateNotificationContent)
    LinearLayout llCreateNotificationContent;
    @InjectView(R.id.svNotificationDetails)
    ScrollView svNotificationDetails;
    @InjectView(R.id.llNotificationType)
    LinearLayout llNotificationType;
    @InjectView(R.id.llNotificationTime)
    LinearLayout llNotificationTime;
    @InjectView(R.id.flEditContainer)
    FrameLayout flEditContainer;
    @InjectView(R.id.calendar)
    CalendarPickerView calendar;

    private boolean startTimeDialogOpen = false;

    @OnClick(R.id.llNotificationType)
    public void onEventTypeClick() {
        Log.d("EVENT", "CLICK");
    }

    @OnClick(R.id.llNotificationTime)
    public void onEventStartTimeClick() {
        openStartTimeDialog();
    }

    @Override
    public int layoutToInflate() {
        return R.layout.fragment_create_notification;
    }

    @Override
    public void doOnCreated(View view) {
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        calendar.init(today, nextYear.getTime()).withSelectedDate(today);
    }

    @Override
    protected boolean isDrawerLocked() {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    public void openStartTimeDialog() {
        AnimationUtils.focusOn(svNotificationDetails, llNotificationTime, llCreateNotificationContent);
        AnimationUtils.stickTo(flEditContainer, llNotificationTime, llCreateNotificationContent);
        startTimeDialogOpen = true;
    }

    public void closeStartTimeDialog() {
        AnimationUtils.undoFocusOn(svNotificationDetails);
        AnimationUtils.undoStickTo(flEditContainer, llCreateNotificationContent);
        startTimeDialogOpen = false;
    }
}

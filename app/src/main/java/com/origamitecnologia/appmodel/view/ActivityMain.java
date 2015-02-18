package com.origamitecnologia.appmodel.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.CallManager;
import com.origamitecnologia.appmodel.control.DisplayCentral;
import com.origamitecnologia.appmodel.control.ServerCentral;
import com.origamitecnologia.appmodel.control.SessionManager;
import com.origamitecnologia.appmodel.model.User;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;

public class ActivityMain extends BaseActivity {
    private static final int OPTION_TIMELINE = 0;
    private static final int OPTION_CREATE_NOTIFICATION = 1;
    private static final int OPTION_LOGOFF = 2;

    @InjectView(R.id.ivMainUserPhoto)
    ImageView ivMainUserPhoto;

    @InjectView(R.id.tvMainUserCompleteName)
    TextView tvMainUserCompleteName;

    @InjectView(R.id.lvMainUserOptions)
    ListView lvMainUserOptions;

    @OnClick(R.id.llMainUserProfile)
    public void onProfileClick() {
        Intent myIntent = new Intent(ActivityMain.this, ActivityProfile.class);
        startActivity(myIntent);
//        CallManager.getInstance().activityProfile();
    }

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnFirstTime() {
        CallManager.getInstance().fragmentTimeline();
    }

    @Override
    public void doOnCreated(Bundle savedInstanceState) {
        User user = SessionManager.getInstance().getCurrentUser();

        Picasso.with(this)
                .load(ServerCentral.getInstance().getImageWithPath(this, user.getPhoto()))
                .fit()
                .transform(new CircleTransform())
                .centerCrop()
                .into(ivMainUserPhoto);

        tvMainUserCompleteName.setText(user.getCompleteName());

        defineOptions();
    }

    private void defineOptions() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.option_list_item, getResources().getStringArray(R.array.main_activity_user_options));
        lvMainUserOptions.setAdapter(adapter);

        lvMainUserOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                defineOnOptionSelected(position);
            }
        });
    }

    private void redirectToLogin() {
        CallManager.getInstance().activityLogin();
    }

    private void openCreateNotification() {
        CallManager.getInstance().fragmentCreateNotification();
    }

    private void defineOnOptionSelected(int position) {
        closeDrawer();
        switch(position) {
            case OPTION_TIMELINE:
                resetToHomeFragment();
                break;
            case OPTION_CREATE_NOTIFICATION:
                openCreateNotification();
                break;
            case OPTION_LOGOFF:
                redirectToLogin();
                break;
        }
    }

    @Override
    protected boolean hasDrawer() {
        return (!(DisplayCentral.getInstance().isLandscape(this) &&
                ((DisplayCentral.getInstance().isScreenExtraLarge(this)) ||
                        (DisplayCentral.getInstance().isScreenLarge(this)))));
    }
}

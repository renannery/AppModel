package com.origamitecnologia.appmodel.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

    private DrawerLayout drawerLayout;

    @InjectView(R.id.ivMainUserPhoto)
    ImageView ivMainUserPhoto;

    @InjectView(R.id.tvMainUserCompleteName)
    TextView tvMainUserCompleteName;

    @InjectView(R.id.lvMainUserOptions)
    ListView lvMainUserOptions;

    @OnClick(R.id.llMainUserProfile)
    public void onProfileClick() {
        startActivity(CallManager.profile(this));
    }

    @Override
    protected int layoutToInflate() {
        return R.layout.activity_main;
    }

    @Override
    protected void doOnFirstTime() {
        setFragment(CallManager.fragmentTimeline(), false);
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
        startActivity(CallManager.login(this));
        finish();
    }

    private void defineOnOptionSelected(int position) {
        switch(position) {
            case OPTION_TIMELINE:
                resetToHomeFragment();
                break;
            case OPTION_CREATE_NOTIFICATION:
                setFragment(CallManager.fragmentCreateNotification(), true);
                break;
            case OPTION_LOGOFF:
                redirectToLogin();
                break;
        }
        closeDrawer();
    }

    @Override
    protected boolean hasDrawer() {
        return (!(DisplayCentral.getInstance().isLandscape(this) &&
                ((DisplayCentral.getInstance().isScreenExtraLarge(this)) ||
                        (DisplayCentral.getInstance().isScreenLarge(this)))));
    }
}

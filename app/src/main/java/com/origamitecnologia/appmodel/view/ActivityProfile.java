package com.origamitecnologia.appmodel.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.ServerCentral;
import com.origamitecnologia.appmodel.control.SessionManager;
import com.origamitecnologia.appmodel.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by pokawa on 16/12/14.
 */
public class ActivityProfile extends BaseActivity {
    @InjectView(R.id.spProfileGender)
    Spinner spProfileGender;

    @InjectView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;

    @InjectView(R.id.metProfileFirstName)
    MaterialEditText metProfileFirstName;

    @InjectView(R.id.metProfileLastName)
    MaterialEditText metProfileLastName;

    @InjectView(R.id.metProfileAbout)
    MaterialEditText metProfileAbout;

    @InjectView(R.id.metProfileUsername)
    MaterialEditText metProfileUsername;

    @InjectView(R.id.metProfileNewPassword)
    MaterialEditText metProfileNewPassword;

    @InjectView(R.id.metProfileConfirmPassword)
    MaterialEditText metProfileConfirmPassword;

    @InjectView(R.id.metProfileEmail)
    MaterialEditText metProfileEmail;

    @InjectView(R.id.metProfilePhone)
    MaterialEditText metProfilePhone;

    @OnClick(R.id.ivProfilePhoto)
    public void onPhotoClick() {

    }

    @Override
    public int layoutToInflate() {
        return R.layout.activity_profile;
    }

    @Override
    protected void doOnFirstTime() {}

    @Override
    public void doOnCreated(Bundle savedInstanceState) {
        User user = SessionManager.getInstance().getCurrentUser();
        Picasso.with(this)
                .load(ServerCentral.getInstance().getImageWithPath(this, user.getPhoto()))
                .transform(new CircleTransform())
                .fit()
                .centerCrop()
                .into(ivProfilePhoto);

        metProfileFirstName.setText(user.getFirstName());
        metProfileLastName.setText(user.getLastName());
        metProfileAbout.setText(user.getAbout());
        metProfileUsername.setText(user.getUsername());
        metProfileEmail.setText(user.getEmail());
        metProfilePhone.setText(user.getPhone());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.profile_activity_gender_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spProfileGender.setAdapter(adapter);
        spProfileGender.setSelection(user.getGender());
    }

    @Override
    protected boolean hasDrawer() {
        return false;
    }
}

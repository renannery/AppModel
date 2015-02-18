package com.origamitecnologia.appmodel.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.CallManager;
import com.origamitecnologia.appmodel.control.EditTextValidator;
import com.origamitecnologia.appmodel.control.UserValidator;
import com.origamitecnologia.appmodel.model.Movie;
import com.origamitecnologia.appmodel.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.IOException;
import java.io.InputStream;

import butterknife.InjectView;
import butterknife.OnClick;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import io.realm.Realm;

public class ActivityLogin extends BaseActivity {

    public static String USER_LOGED_PREFERENCES = "USER_LOGED";
    public static String USER_LOGED_VALIDATION = "LOGED_VALIDATION";
    public static String USER_LOGED_ID = "LOGED_ID";
    public static long USER_LOGED_NO_USER = 999999999;
    public static String USER_LOGED_NO_USER_STRING = "";

    private SharedPreferences sharedpreferences;

    @InjectView(R.id.metUsername)
    MaterialEditText metUsername;

    @InjectView(R.id.metPassword)
    MaterialEditText metPassword;

    @OnClick(R.id.btProfileSave)
    public void onLoginClick() {
        metPassword.validate();
        User user = UserValidator.getInstance().validateUser(getApplication(), metUsername.getText().toString().toLowerCase(), metPassword.getText().toString());

        if(metUsername.validate() && metPassword.validate()) {
            if (user != null) {
                setUserLoged(user.getUsername());
                goToMainScreen();
            } else {
                Crouton.makeText(this, getResources().getString(R.string.login_crouton_message_error), Style.ALERT).show();
            }
        }
    }

    @Override
    public int layoutToInflate() {
        return R.layout.activity_login;
    }

    @Override
    protected void doOnFirstTime() {

    }

    @Override
    public void doOnCreated(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(USER_LOGED_PREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getBoolean(USER_LOGED_VALIDATION, false)) {
            goToMainScreen();
        } else {
            setUserTest();
            loadMoviesTest();
        }

        metUsername.addValidator(new EditTextValidator(getResources().getString(R.string.login_edit_text_message_error)));
        metPassword.addValidator(new EditTextValidator(getResources().getString(R.string.login_edit_text_message_error)));
    }

    @Override
    protected boolean hasDrawer() {
        return false;
    }

    private void goToMainScreen() {
        startActivity(CallManager.main(this));
        finish();
    }

    private void loadMoviesTest() {
        Realm realm = Realm.getInstance(this);

        InputStream is = getResources().openRawResource(R.raw.load_notifications);
        realm.beginTransaction();
        try {
            realm.createAllFromJson(Movie.class, is);
            realm.commitTransaction();
        } catch (IOException e) {
            realm.cancelTransaction();
        }
    }

    private void setUserTest() {
        Realm realm = Realm.getInstance(this);

        InputStream is = getResources().openRawResource(R.raw.load_users);
        realm.beginTransaction();

        try {
            realm.createAllFromJson(User.class, is);
            realm.commitTransaction();
        } catch (IOException e) {
            realm.cancelTransaction();
        }
    }

    private void setUserLoged(String username) {
        sharedpreferences.edit().putBoolean(USER_LOGED_VALIDATION, true).putString(USER_LOGED_ID, username).commit();
    }
}
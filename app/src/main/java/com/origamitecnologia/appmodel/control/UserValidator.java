package com.origamitecnologia.appmodel.control;

import android.content.Context;

import com.origamitecnologia.appmodel.model.User;

import io.realm.Realm;
import io.realm.RealmQuery;


public class UserValidator {

    private static int SELECT_LIMIT = 20;
    private static UserValidator instance;

    private UserValidator() {

    }

    public static UserValidator getInstance() {
        if(instance == null) {
            instance = new UserValidator();
        }
        return instance;
    }

    public User validateUser(Context context, String username, String password) {
        Realm realm = Realm.getInstance(context);
        RealmQuery<User> query = realm.where(User.class);

        return query.equalTo("username", username).equalTo("password", password).findFirst();
    }

    public User getUserByUsername(Context context, String username) {
        Realm realm = Realm.getInstance(context);
        RealmQuery<User> query = realm.where(User.class);
        return query.equalTo("username", username).findFirst();
    }

}

package com.origamitecnologia.appmodel.control;

import com.origamitecnologia.appmodel.model.User;

public class SessionManager {

    private static SessionManager instance;

    private User user;

    private SessionManager() {

    }

    public static SessionManager getInstance() {
        if(instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        return user;
    }

    public void clearCurrentUser() {
        this.user = null;
    }
}

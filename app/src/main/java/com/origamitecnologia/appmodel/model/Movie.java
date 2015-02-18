package com.origamitecnologia.appmodel.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private String description;
    private String photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

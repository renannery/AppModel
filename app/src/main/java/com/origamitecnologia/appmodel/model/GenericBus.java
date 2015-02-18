package com.origamitecnologia.appmodel.model;


import java.util.ArrayList;

public class GenericBus<T> {

    private int key;
    private ArrayList<T> objects;
    private Object object;

    public static final int FRAGMENT_NOTIFICATION = 0;

    public GenericBus(int key) {
        this.key = key;
        this.object = null;
    }

    public GenericBus(int key, ArrayList<T> objects) {
        this.key = key;
        this.objects = objects;
    }

    public GenericBus(int key, Object object) {
        this.key = key;
        this.object = object;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<T> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<T> objects) {
        this.objects = objects;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
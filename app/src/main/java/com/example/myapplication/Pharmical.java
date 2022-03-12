package com.example.myapplication;

import android.app.Application;

public class Pharmical extends Application {

    private static Pharmical instance;

    @Override
    public void onCreate() {
        super.onCreate();

        if (instance==null)
            instance = this;
    }

    public static Pharmical getInstance()
    {
        return instance;
    }
}




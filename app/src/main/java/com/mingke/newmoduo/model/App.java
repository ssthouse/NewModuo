package com.mingke.newmoduo.model;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import timber.log.Timber;

/**
 * Created by ssthouse on 2016/1/24.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        ActiveAndroid.initialize(this);
    }
}

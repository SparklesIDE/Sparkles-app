package com.sparkleside;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class App extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        /*
         * disabled because syntaxspim is indecisive 😂
         * DynamicColors.applyToActivitiesIfAvailable(this);
         */
    }
}

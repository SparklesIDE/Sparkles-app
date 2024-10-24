package com.sparkleside;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class App extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate(this);
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}

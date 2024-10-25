package com.sparkleside;

import android.app.Application;

import com.google.android.material.color.DynamicColors;
import com.sparkleside.preferences.Preferences;

public class App extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        if(Preferences.Theme.isMonetEnable(this)) DynamicColors.applyToActivitiesIfAvailable(this);
    }
}

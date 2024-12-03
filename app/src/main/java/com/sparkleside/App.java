package com.sparkleside;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.color.DynamicColors;
import com.sparkleside.preferences.Preferences;

public class App extends Application {
  private static Context context;
  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    if (Preferences.Theme.isMonetEnable(this)) DynamicColors.applyToActivitiesIfAvailable(this);
    AppCompatDelegate.setDefaultNightMode(Preferences.Theme.getThemeMode(this));
  }
  
  public static Context getContext() {
    return context;
  }
}

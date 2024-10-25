package com.sparkleside.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.sparkleside.R;
import com.sparkleside.preferences.Preferences;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(Preferences.Theme.getThemeMode(this));

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

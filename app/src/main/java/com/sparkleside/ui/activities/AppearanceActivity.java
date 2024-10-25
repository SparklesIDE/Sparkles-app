package com.sparkleside.ui.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatDelegate;

import com.sparkleside.R;
import com.sparkleside.databinding.ActivityAppearanceBinding;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.preferences.Preferences;

public class AppearanceActivity extends BaseActivity {

    private ActivityAppearanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int theme = AppCompatDelegate.getDefaultNightMode();

        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_YES -> binding.linear5.check(R.id.materialbutton2);
            case AppCompatDelegate.MODE_NIGHT_NO -> binding.linear5.check(R.id.materialbutton1);
            case AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> binding.linear5.check(R.id.materialbutton3);
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.linear5.check(R.id.materialbutton3);
        }

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.materialbutton1.setOnClickListener(v -> {
            Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_NO);
        });

        binding.materialbutton2.setOnClickListener(v -> {
            Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_YES);
        });

        binding.materialbutton3.setOnClickListener(v -> {
            Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        });
    }
}

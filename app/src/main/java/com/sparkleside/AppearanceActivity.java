package com.sparkleside;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.appbar.AppBarLayout;
import com.sparkleside.databinding.ActivityAppearanceBinding;

public class AppearanceActivity extends AppCompatActivity {

    private ActivityAppearanceBinding binding;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int theme = AppCompatDelegate.getDefaultNightMode();
        if (theme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            binding.linear5.check(R.id.materialbutton3);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.linear5.check(R.id.materialbutton2);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_NO) {
            binding.linear5.check(R.id.materialbutton1);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED) {
            binding.linear5.check(R.id.materialbutton3);
        }

        EdgeToEdge.enable(this);
        sp = getSharedPreferences("com.sparkleside.app_prefs", MODE_PRIVATE);
        editor = sp.edit();

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        binding.materialbutton1.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putString("theme", "light");
            editor.apply();
        });

        binding.materialbutton2.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putString("theme", "dark");
            editor.apply();
        });

        binding.materialbutton3.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            editor.putString("theme", "auto");
            editor.apply();
        });
    }
}
package com.sparkleside.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActivityOptions;
import android.transition.Transition;
import android.transition.TransitionSet;
import androidx.activity.EdgeToEdge;
import com.google.android.material.transition.MaterialSharedAxis;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.databinding.ActivitySettingsBinding;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);
        MaterialSharedAxis exit = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        exit.addTarget(R.id.coordinator);

        // Set the exit transition for the window
        getWindow().setExitTransition(exit);

        // TODO: Add reenter transition for backwards direction (Activity B -> Activity A)
        // Consider using TransitionSet for chaining transitions
        Transition reenter = null; // Replace with your desired reenter transition logic

        // Optionally, chain exit and reenter transitions for a smoother animation
        if (reenter != null) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(exit);
            transitionSet.addTransition(reenter);
            getWindow().setReenterTransition(transitionSet);
        }
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        binding.about.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
            binding.about.setTransitionName("xy");
            android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation(this, binding.about, "xy");
            startActivity(intent , optionsCompat.toBundle());
        });

        binding.main.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, AppearanceActivity.class);
            binding.main.setTransitionName("xz");
            android.app.ActivityOptions optionsCompat = android.app.ActivityOptions.makeSceneTransitionAnimation(this, binding.main, "xz");
            startActivity(intent , optionsCompat.toBundle());
        });
        binding.lib.setOnClickListener(v -> {
           LibsBuilder libe = new LibsBuilder();
            libe.start(this);
        });
    }
}
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
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementsUseOverlay(false);
            MaterialSharedAxis exitTransition = new MaterialSharedAxis(MaterialSharedAxis.Y, true);
		    exitTransition.addTarget(R.id.coordinator);
		    getWindow().setExitTransition(exitTransition);
		
		    MaterialSharedAxis reenterTransition = new MaterialSharedAxis(MaterialSharedAxis.Y, false);
		    reenterTransition.addTarget(R.id.coordinator);
		    getWindow().setReenterTransition(reenterTransition);

        super.onCreate(savedInstanceState);
        
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
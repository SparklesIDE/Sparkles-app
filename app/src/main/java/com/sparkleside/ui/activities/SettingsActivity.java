package com.sparkleside.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivitySettingsBinding;
import com.sparkleside.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

  private ActivitySettingsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivitySettingsBinding.inflate(getLayoutInflater());
    getWindow().setAllowEnterTransitionOverlap(false);
    MaterialSharedAxis enterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    enterTransition.addTarget(R.id.coordinator);
    enterTransition.setDuration(200L);
    getWindow().setEnterTransition(enterTransition);
    MaterialSharedAxis returnTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    returnTransition.setDuration(200L);
    returnTransition.addTarget(R.id.coordinator);
    getWindow().setReturnTransition(returnTransition);

    MaterialSharedAxis exitTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    exitTransition.addTarget(R.id.coordinator);
    getWindow().setExitTransition(exitTransition);
    MaterialSharedAxis reenterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    reenterTransition.addTarget(R.id.coordinator);
    getWindow().setReenterTransition(reenterTransition);
    super.onCreate(savedInstanceState);

    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    binding.toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

    binding.about.setOnClickListener(
        v -> {
          Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
          android.app.ActivityOptions optionsCompat =
              android.app.ActivityOptions.makeSceneTransitionAnimation(this, binding.about,"");
          startActivity(intent, optionsCompat.toBundle());
        });

    binding.main.setOnClickListener(
        v -> {
          Intent intent = new Intent(SettingsActivity.this, AppearanceActivity.class);
          android.app.ActivityOptions optionsCompat =
              android.app.ActivityOptions.makeSceneTransitionAnimation(this, binding.main,"");
          startActivity(intent, optionsCompat.toBundle());
        });
    binding.lib.setOnClickListener(
        v -> {
          LibsBuilder libe = new LibsBuilder();
          libe.start(this);
        });
  }
}

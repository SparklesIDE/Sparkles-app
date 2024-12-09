package com.sparkleside.ui.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityAppearanceBinding;
import com.sparkleside.preferences.Preferences;
import com.sparkleside.ui.base.BaseActivity;
import dev.trindadedev.ui_utils.preferences.withicon.PreferenceSwitch;

public class AppearanceActivity extends BaseActivity {

  private ActivityAppearanceBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityAppearanceBinding.inflate(getLayoutInflater());
    getWindow().setAllowEnterTransitionOverlap(false);
    MaterialSharedAxis enterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    enterTransition.addTarget(R.id.coordinator);
    enterTransition.setDuration(200L);
    getWindow().setEnterTransition(enterTransition);
    MaterialSharedAxis returnTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    returnTransition.setDuration(200L);
    returnTransition.addTarget(R.id.coordinator);
    getWindow().setReturnTransition(returnTransition);
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    int theme = AppCompatDelegate.getDefaultNightMode();

    switch (theme) {
      case AppCompatDelegate.MODE_NIGHT_YES -> binding.linear5.check(R.id.materialbutton2);
      case AppCompatDelegate.MODE_NIGHT_NO -> binding.linear5.check(R.id.materialbutton1);
      case AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> binding.linear5.check(R.id.materialbutton3);
      case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ->
          binding.linear5.check(R.id.materialbutton3);
    }

    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    binding.materialbutton1.setOnClickListener(
        v -> {
          Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_NO);
        });

    binding.materialbutton2.setOnClickListener(
        v -> {
          Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_YES);
        });

    binding.materialbutton3.setOnClickListener(
        v -> {
          Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        });

    binding.linear1.addView(getMonetPreference());
  }

  private PreferenceSwitch getMonetPreference() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.ic_pallete);
    pref.setTitle(getString(R.string.monet_title));
    pref.setDescription(getString(R.string.monet_desc));
    pref.setValue(Preferences.Theme.isMonetEnable(this));
    pref.setSwitchChangedListener(
        (c, isChecked) -> {
          Preferences.Theme.setMonetEnable(this, isChecked);
          Toast.makeText(this, getString(R.string.need_restart), 4000).show();
        });
    return pref;
  }
}

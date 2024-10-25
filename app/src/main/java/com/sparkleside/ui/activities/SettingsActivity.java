package com.sparkleside.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import androidx.activity.EdgeToEdge;

import com.sparkleside.R;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.databinding.ActivitySettingsBinding;

import dev.trindadedev.ui_utils.preferences.withicon.Preference;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> super.onBackPressed());
        
        createPreferences();
    }
    
    private void createPreferences() {
            binding.content.addView(createPreference(R.string.set_main, R.string.set_main_desc, R.drawable.android_24px));
            binding.content.addView(createPreference(R.string.set_editor, R.string.set_editor_desc, R.drawable.edit_24px));
            binding.content.addView(createPreference(R.string.set_terminal,R.string.set_terminal_desc,R.drawable.terminal_24px));
            binding.content.addView(createPreference(R.string.set_lib, R.string.set_lib_desc, R.drawable.book_24px));
            binding.content.addView(createPreference(R.string.com_word_about,R.string.set_about_desc,R.drawable.info_24px));
    }
    
    private Preference createPreference(int titleResId, int descriptionResId, int iconResId) {
            Preference preference = new Preference(this);

            preference.setTitle(getString(titleResId));
            preference.setDescription(getString(descriptionResId));
            preference.setIcon(iconResId);

            preference.setOnClickListener(v -> {});

            return preference;
    }
}

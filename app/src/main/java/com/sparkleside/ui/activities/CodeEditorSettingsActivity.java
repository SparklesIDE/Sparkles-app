package com.sparkleside.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.sparkleside.ui.base.BaseActivity;
import dev.trindadedev.ui_utils.preferences.withicon.PreferenceSwitch;
import com.sparkleside.preferences.Preferences;
import com.sparkleside.R;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.sparkleside.databinding.ActivityCodeEditorSettingsBinding;

public class CodeEditorSettingsActivity extends BaseActivity {
    private ActivityCodeEditorSettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityCodeEditorSettingsBinding.inflate(getLayoutInflater());
    getWindow().setAllowEnterTransitionOverlap(false);
    MaterialSharedAxis enterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    enterTransition.addTarget(R.id.coordinator);
    enterTransition.setDuration(400L);
    getWindow().setEnterTransition(enterTransition);
    MaterialSharedAxis returnTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    returnTransition.setDuration(400L);
    returnTransition.addTarget(R.id.coordinator);
    getWindow().setReturnTransition(returnTransition);
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    binding.linear1.addView(getEditorWrap());
    binding.linear1.addView(getEditorFirstLine());
    binding.linear1.addView(getEditorLineNumbers());
    binding.linear1.addView(getEditorToolbar());
    binding.linear1.addView(getEditorTabs());
    themeSora();
    }
    private PreferenceSwitch getEditorWrap() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.wrap_text_24px);
    pref.setTitle(getString(R.string.editor_wordwrap));
    pref.setDescription(getString(R.string.editor_wordwrap_desc));
    pref.setValue(Preferences.Editor.isWordWrapEnabled(this));
    pref.setBackgroundPosition("0");
    pref.setSwitchChangedListener(
        (c, isChecked) -> { 
        Preferences.Editor.setWordWrapEnable(this,isChecked);
        askForRestart();
        });
    return pref;
    }
    private PreferenceSwitch getEditorFirstLine() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.segment_24px);
    pref.setTitle(getString(R.string.editor_fl));
    pref.setDescription(getString(R.string.editor_fl_desc));
    pref.setValue(Preferences.Editor.isShowFirstLineEnable(this));
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> {
            Preferences.Editor.setShowFirstLineEnable(this,isChecked);
            askForRestart();
         });
    return pref;
    }
    private PreferenceSwitch getEditorOverScroll() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.swipe_up_24px);
    pref.setTitle(getString(R.string.editor_overscroll));
    pref.setDescription(getString(R.string.editor_overscroll_desc));
    pref.setValue(Preferences.Editor.isUseOverscrollEnabled(this));
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> {
            Preferences.Editor.setUseOverscrollEnable(this,isChecked);
            askForRestart();    
         });
    return pref;
    }
    private PreferenceSwitch getEditorLineNumbers() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.format_list_numbered_24px);
    pref.setTitle(getString(R.string.editor_ln));
    pref.setDescription(getString(R.string.editor_ln_desc));
    pref.setValue(Preferences.Editor.isShowLineEnable(this));
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> {
            Preferences.Editor.setShowLineEnable(this , isChecked);
            askForRestart();
         });
    return pref;
    }
    private PreferenceSwitch getEditorToolbar() {
        PreferenceSwitch pref = new PreferenceSwitch(this);
        pref.setIcon(R.drawable.toolbar_24px);
        pref.setTitle(getString(R.string.editor_toolbar));
        pref.setDescription(getString(R.string.editor_toolbar_desc));
        pref.setValue(Preferences.Editor.isShowToolbarEnabled(this));
        pref.setBackgroundPosition("2");
        pref.setSwitchChangedListener(
        (c, isChecked) -> { 
            Preferences.Editor.setShowToolbarEnable(this,isChecked);
            askForRestart();
            
        });
        return pref;
    }
    
    private PreferenceSwitch getEditorTabs() {
        PreferenceSwitch pref = new PreferenceSwitch(this);
        pref.setIcon(R.drawable.tabs_24px);
        pref.setTitle(getString(R.string.editor_tab));
        pref.setDescription(getString(R.string.editor_tab_desc));
        pref.setValue(false);
        pref.setBackgroundPosition("3");
        pref.setSwitchChangedListener(
        (c, isChecked) -> {
            Toast.makeText(this,"ComingSoon", 800);
         });
        return pref;
    }
    
    private void askForRestart() {
        Snackbar.make(binding.linear1, "To Apply Changes Restart the app", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Restart", v-> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }).show();
    }
    public void themeSora(){
        switch(Preferences.Editor.getEditorThemeMode(this)){
            case 0 -> binding.linear5.check(R.id.materialbutton1);
            case 1 -> binding.linear5.check(R.id.materialbutton2);
            case 2 -> binding.linear5.check(R.id.materialbutton3);
            case 3 -> binding.linear5.check(R.id.materialbutton4);
            case 4 -> binding.linear5.check(R.id.materialbutton5);
            case 5 -> binding.linear5.check(R.id.materialbutton6);
            default -> binding.linear5.check(R.id.materialbutton1);
        }

        
        binding.materialbutton1.setOnClickListener(
        v -> {
          Preferences.Editor.setThemeMode(this, 0);
          askForRestart();
        });
        binding.materialbutton2.setOnClickListener(
        v -> {
          Preferences.Editor.setThemeMode(this, 1);
          askForRestart();
        });
        binding.materialbutton3.setOnClickListener(
        v -> {
          Preferences.Editor.setThemeMode(this, 2);
          askForRestart();
        });
        binding.materialbutton4.setOnClickListener(
        v -> {
          Preferences.Editor.setThemeMode(this, 3);
          askForRestart();
        });
        binding.materialbutton5.setOnClickListener(
        v -> {
          Preferences.Editor.setThemeMode(this, 4);
          askForRestart();
        });
        binding.materialbutton6.setOnClickListener(
        v -> {
          Preferences.Editor.setThemeMode(this, 5);
          askForRestart();
        });
        
    }

}

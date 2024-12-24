package com.sparkleside.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
    binding.linear1.addView(getEditorOverScroll());
    binding.linear1.addView(getEditorToolbar());
    binding.linear1.addView(getEditorTabs());
    }
    private PreferenceSwitch getEditorWrap() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.wrap_text_24px);
    pref.setTitle(getString(R.string.editor_wordwrap));
    pref.setDescription(getString(R.string.editor_wordwrap_desc));
    pref.setValue(false);
    pref.setBackgroundPosition("0");
    pref.setSwitchChangedListener(
        (c, isChecked) -> { });
    return pref;
    }
    private PreferenceSwitch getEditorFirstLine() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.segment_24px);
    pref.setTitle(getString(R.string.editor_fl));
    pref.setDescription(getString(R.string.editor_fl_desc));
    pref.setValue(false);
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> { });
    return pref;
    }
    private PreferenceSwitch getEditorOverScroll() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.swipe_up_24px);
    pref.setTitle(getString(R.string.editor_overscroll));
    pref.setDescription(getString(R.string.editor_overscroll_desc));
    pref.setValue(false);
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> { });
    return pref;
    }
    private PreferenceSwitch getEditorLineNumbers() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.format_list_numbered_24px);
    pref.setTitle(getString(R.string.editor_ln));
    pref.setDescription(getString(R.string.editor_ln_desc));
    pref.setValue(false);
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> { });
    return pref;
    }
    private PreferenceSwitch getEditorToolbar() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.toolbar_24px);
    pref.setTitle(getString(R.string.editor_toolbar));
    pref.setDescription(getString(R.string.editor_toolbar_desc));
    pref.setValue(false);
    pref.setBackgroundPosition("2");
    pref.setSwitchChangedListener(
        (c, isChecked) -> { });
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
        (c, isChecked) -> { });
    return pref;
    }

}

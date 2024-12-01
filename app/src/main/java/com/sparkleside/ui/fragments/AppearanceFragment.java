package com.sparkleside.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.sparkleside.R;
import com.sparkleside.databinding.FragmentAppearanceBinding;
import com.sparkleside.preferences.Preferences;

import dev.trindadedev.ui_utils.preferences.withicon.PreferenceSwitch;

public class AppearanceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentAppearanceBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        var binding = FragmentAppearanceBinding.bind(view);

        int theme = AppCompatDelegate.getDefaultNightMode();

        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_YES -> binding.linear5.check(R.id.materialbutton2);
            case AppCompatDelegate.MODE_NIGHT_NO -> binding.linear5.check(R.id.materialbutton1);
            case AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> binding.linear5.check(R.id.materialbutton3);
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.linear5.check(R.id.materialbutton3);
        }

        binding.materialbutton1.setOnClickListener(v -> {
            Preferences.Theme.setThemeMode(requireContext(), AppCompatDelegate.MODE_NIGHT_NO);
        });

        binding.materialbutton2.setOnClickListener(v -> {
            Preferences.Theme.setThemeMode(requireContext(), AppCompatDelegate.MODE_NIGHT_YES);
        });

        binding.materialbutton3.setOnClickListener(v -> {
            Preferences.Theme.setThemeMode(requireContext(), AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        });

        binding.linear1.addView(getMonetPreference());
    }

    private PreferenceSwitch getMonetPreference() {
        PreferenceSwitch pref = new PreferenceSwitch(requireContext());
        pref.setIcon(R.drawable.ic_pallete);
        pref.setTitle(getString(R.string.monet_title));
        pref.setDescription(getString(R.string.monet_desc));
        pref.setValue(Preferences.Theme.isMonetEnable(requireContext()));
        pref.setSwitchChangedListener((c, isChecked) -> {
            Preferences.Theme.setMonetEnable(requireContext(), isChecked);
            Toast.makeText(requireContext(), getString(R.string.need_restart), Toast.LENGTH_SHORT).show();
        });
        return pref;
    }
}

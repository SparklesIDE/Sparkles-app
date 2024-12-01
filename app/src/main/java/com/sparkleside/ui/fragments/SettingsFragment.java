package com.sparkleside.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.sparkleside.R;
import com.sparkleside.databinding.FragmentSettingsBinding;
import com.sparkleside.ui.activities.SettingsActivity;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentSettingsBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        var binding = FragmentSettingsBinding.bind(view);
        var controller = Navigation.findNavController(view);

        binding.about.setOnClickListener(v -> ((SettingsActivity) requireActivity()).navigate(R.id.action_settingsFragment_to_aboutFragment));
        binding.main.setOnClickListener(v -> ((SettingsActivity) requireActivity()).navigate(R.id.action_settingsFragment_to_appearanceFragment));

        binding.lib.setOnClickListener(v -> {
            LibsBuilder libe = new LibsBuilder();
            libe.start(requireActivity());
        });
    }
}

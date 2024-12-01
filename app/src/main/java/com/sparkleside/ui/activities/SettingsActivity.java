package com.sparkleside.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.blankj.utilcode.util.ToastUtils;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivitySettingsBinding;
import com.sparkleside.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        assert getSupportActionBar() != null;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        var fragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        assert fragment != null;

        navController = fragment.getNavController();
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            binding.collapsingtoolbar.setTitle(navDestination.getLabel());
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(OnBackInvokedDispatcher.PRIORITY_DEFAULT, () -> {
                if (!navController.popBackStack()) {
                    finishAfterTransition();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    public void navigate(int id) {
        navController.navigate(id);
    }
}
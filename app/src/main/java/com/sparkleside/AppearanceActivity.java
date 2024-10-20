package com.sparkleside;

import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.sparkleside.databinding.ActivityAppearanceBinding;
import androidx.appcompat.app.AppCompatDelegate;

public class AppearanceActivity extends AppCompatActivity {
    private ActivityAppearanceBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppearanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int theme = AppCompatDelegate.getDefaultNightMode();
        if (theme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            binding.linear5.check(R.id.materialbutton3);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_YES){
            binding.linear5.check(R.id.materialbutton2);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_NO) {
            binding.linear5.check(R.id.materialbutton1);
        } else if (theme == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED) {
            binding.linear5.check(R.id.materialbutton3);
        }
        EdgeToEdge.enable(this);
        
	    setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> {
			onBackPressed();
		});
        binding.materialbutton1.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        });
        binding.materialbutton2.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        });
        binding.materialbutton3.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        });
    }
}

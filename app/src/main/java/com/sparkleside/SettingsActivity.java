package com.sparkleside;

import android.R;
import androidx.activity.EdgeToEdge;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.sparkleside.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
		    setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> {
			onBackPressed();
			}
		);


    }
    @Override
    public void onBackPressed() {
    // Your custom back button logic here
        super.onBackPressed();
}

}

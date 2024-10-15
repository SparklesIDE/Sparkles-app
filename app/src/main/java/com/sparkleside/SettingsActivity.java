package com.sparkleside;

import android.R;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.sparkleside.databinding.ActivitySettingsBinding;
//import dev.trindadedev.easyui.components.preferences.withicon.Preference;


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
/*binding.main.setTitle("Test");
     binding.main.setDescription("Testing this lib");
     binding.main.setIcon(R.drawable.save_24px);*/
      binding.about.setOnClickListener(v->{
          Intent intent = new Intent(SettingsActivity.this,AboutActivity.class);
            startActivity(intent);
      }
        );
    }
    @Override
    public void onBackPressed() {
    // Your custom back button logic here
        super.onBackPressed();
}

}

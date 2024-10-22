package com.sparkleside;

import com.google.android.material.appbar.AppBarLayout;
import android.content.Intent;
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener;
import androidx.activity.EdgeToEdge;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.sparkleside.databinding.ActivitySettingsBinding;
import android.animation.ArgbEvaluator;


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
      binding.about.setOnClickListener(v->{
          Intent intent = new Intent(SettingsActivity.this,AboutActivity.class);
            startActivity(intent);
      }
        );
       binding.main.setOnClickListener(v->{
          Intent intent = new Intent(SettingsActivity.this,AppearanceActivity.class);
            startActivity(intent);
      }
        );
        
      binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                float scrollPercentage = Math.abs(verticalOffset) / (float) totalScrollRange;
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();                
                int toolbarColor = (int) argbEvaluator.evaluate(scrollPercentage, getColor(R.color.md_theme_surface), getColor(R.color.md_theme_surfaceVariant));                
                binding.appbar.setBackgroundColor(toolbarColor);
            }
        });  
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
}

}

package com.sparkleside;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.sparkleside.databinding.ActivityAboutBinding;
import java.net.URI;
public class AboutActivity extends AppCompatActivity {
    private ActivityAboutBinding binding;
    private Intent intent ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> {
			onBackPressed();
			} 
		);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/skinvent").into(binding.imgSk);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/hanzodev1375").into(binding.imgHanzo);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/neoapps-dev").into(binding.imgNeo);
        binding.tg.setOnClickListener(v->{
      String url = "http://www.telegram.me/sparkleside";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url));
      startActivity(i);
                
        });
        binding.github.setOnClickListener(v->{
      String url2 = "http://github.com/sparkleside/sparkles-app";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url2));
      startActivity(i);
                
        });
        binding.hanzo.setOnClickListener(v->{
      String url3 = "http://github.com/hanzodev1375";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url3));
      startActivity(i);
                
        });
        binding.skinvent.setOnClickListener(v->{
      String url4 = "http://github.com/skinvent";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url4));
      startActivity(i);
                
        });
        binding.neo.setOnClickListener(v->{
      String url5 = "http://github.com/neoapps-dev";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url5));
      startActivity(i);
                
        });
    }
    @Override
    public void onBackPressed() {
    // Your custom back button logic here
        super.onBackPressed();
}
}

package com.sparkleside;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.sparkleside.databinding.ActivityAboutBinding;
import java.net.URI;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
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
        PeekAndPop peekAndPop = new PeekAndPop.Builder(this)
                .peekLayout(R.layout.about_preview)
                .longClickViews(binding.syn)
                .build();
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/syntaxspin").into(binding.imgSyn);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/yamenher").into(binding.aboutAvatarYamen);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/hanzodev1375").into(binding.imgHanzo);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/trindadedev13").into(binding.imgTrindade);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/thdev-only").into(binding.aboutAvatarThiarley);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/RohitKushvaha01").into(binding.aboutAvatarRohit);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/jetrom17").into(binding.aboutAvatarJetrom);
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
                binding.yamen.setOnClickListener(v->{
      String url9 = "http://github.com/yamenher";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url9));
      startActivity(i);
                
        });
        binding.syn.setOnClickListener(v->{
      String url4 = "http://github.com/syntaxspin";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url4));
      startActivity(i);
                
        });
        binding.trindade.setOnClickListener(v->{
      String url5 = "http://github.com/trindadedev13";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url5));
      startActivity(i);
                
        });
        binding.thiarley.setOnClickListener(v->{
      String url6 = "http://github.com/thdev-only";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url6));
      startActivity(i);
                
                
        });
        binding.rohit.setOnClickListener(v->{
      String url7 = "http://github.com/RohitKushvaha01";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url7));
      startActivity(i);
                
        });
        binding.jetrom.setOnClickListener(v->{
      String url8 = "http://github.com/jetrom17";
      Intent i = new Intent(Intent.ACTION_VIEW);
      i.setData(Uri.parse(url8));
      startActivity(i);
                
        });
    }
    @Override
    public void onBackPressed() {
    // Your custom back button logic here
        super.onBackPressed();
}
}

package com.sparkleside;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.sparkleside.databinding.ActivityAboutBinding;
import com.sparkleside.component.ContributorView;

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
            super.onBackPressed();
        });
		
        PeekAndPop peekAndPop = new PeekAndPop.Builder(this)
            .peekLayout(R.layout.about_preview)
            .longClickViews(binding.syn)
            .build();
            
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/syntaxspin").into(binding.imgSyn);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/hanzodev1375").into(binding.imgHanzo);
        Glide.with(getApplicationContext()).load("https://avatars.githubusercontent.com/trindadedev13").into(binding.imgTrindade);
        
        binding.tg.setOnClickListener(v->{
            String url = "http://www.telegram.me/sparkleside";
            openURL(url);
        });
        
        binding.github.setOnClickListener(v->{
            String url2 = "http://github.com/sparkleside/sparkles-app";
            openURL(url2);
        });
        
        binding.hanzo.setOnClickListener(v->{
            String url3 = "http://github.com/hanzodev1375";
            openURL(url3);
        });
        
        binding.syn.setOnClickListener(v->{
            String url4 = "http://github.com/syntaxspin";
            openURL(url4);
        });
        
        binding.trindade.setOnClickListener(v->{
            String url5 = "http://github.com/trindadedev13";
            openURL(url5);
        });
        
        createContributors();
    }
    
    private void createContributors() {
        newContributor(
            "Yamenher",
            "Developer",
            "http://github.com/yamenher"
        );
        
        newContributor(
            "Thiarley Rocha",
            "Developer",
            "http://github.com/thdev-only"
        );
        
        newContributor(
            "Rohit Kushvaha",
            "Developer",
            "http://github.com/RohitKushvaha01"
        );
        
        newContributor(
            "Jaiel Lima Miranda",
            "Developer",
            "http://github.com/jetrom17"
        );
    }
    
    private void newContributor(String name, String description, String url) {
        var c = new ContributorView(this);
        c.setName(name);
        c.setDescription(description);
        c.setImageURL(url + ".png");
        c.setURL(url);
        binding.contributors.addView(c);
    }
    
    private void openURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}

package com.sparkleside.ui.activities;

import com.sparkleside.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.sparkleside.databinding.ActivityAboutBinding;
import com.sparkleside.ui.components.TeamView;

import java.net.URI;

import com.peekandpop.shalskar.peekandpop.PeekAndPop;

/*
* A Screen with info about app
* @author Aquiles Trindade (trindadedev).
*/

public class AboutActivity extends AppCompatActivity {
    private ActivityAboutBinding binding;
    private Intent intent ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        configureToolbar();
        configureDevelopers();
		configureLinks();
		TeamConfig();
    }
    
    private void configureToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        binding.toolbar.setNavigationOnClickListener(v -> {
            super.onBackPressed();
        });
    }
    
    private void configureDevelopers() {
        Glide
            .with(this)
            .load("https://github.com/syntaxspin.png")
            .into(binding.imgSyn);
        Glide
            .with(this)
            .load("https://github.com/yamenher.png")
            .into(binding.imgYamen);
        Glide
            .with(this)
            .load("https://github.com/trindadedev13.png")
            .into(binding.imgTrindade);
            
        peekAndPop(
            "SyntaxSpin",
            "https://github.com/syntaxspin.png",
            binding.imgSyn,
            "Idk what should I say :/"
        );
        peekAndPop(
            "Yamen",
            "https://github.com/YamenHer.png",
            binding.imgYamen,
            "A Kool Utilities maker"
        );
        peekAndPop(
            "Aquiles Trindade",
            "https://github.com/trindadedev13.png",
            binding.imgTrindade,
            "I use Compose btw"
        );
    }
    
    private void configureLinks() {
        binding.tg.setOnClickListener(v->{
            String url = "https://www.telegram.me/sparkleside";
            openURL(url);
        });
        
        binding.github.setOnClickListener(v->{
            String url2 = "https://github.com/sparkleside/sparkles-app";
            openURL(url2);
        });
        
        binding.hanzo.setOnClickListener(v->{
            String url3 = "https://github.com/yamenher";
            openURL(url3);
        });
        
        binding.syn.setOnClickListener(v->{
            String url4 = "https://github.com/syntaxspin";
            openURL(url4);
        });
        
        binding.trindade.setOnClickListener(v->{
            String url5 = "https://github.com/trindadedev13";
            openURL(url5);
        });
    }
    
    private void TeamConfig() {
        Team(
            "Hanzo",
            "Developer",
            "https://github.com/HanzoDev1375",
            true
        );
        
        Team(
            "Thiarley Rocha",
            "Developer",
            "https://github.com/thdev-only",
            true
        );
        
        Team(
            "Rohit Kushvaha",
            "Developer",
            "https://github.com/RohitKushvaha01",
            true
        );
        
        Team(
            "Jaiel Lima Miranda",
            "Developer",
            "https://github.com/jetrom17",
            false
        );
    }
    
    private void peekAndPop(String name, String imageUrl, View v, String phrase) {
        PeekAndPop peekAndPop = new PeekAndPop.Builder(this)
            .peekLayout(R.layout.layout_about_preview)
            .longClickViews(v)
            .build();
        ImageView peekChild = peekAndPop.getPeekView().findViewById(R.id.icon);
        Glide.with(this)
            .load(imageUrl)
            .into(peekChild);
        TextView peekText = peekAndPop.getPeekView().findViewById(R.id.title);
        peekText.setText(phrase);
    }
    
    private void Team(String name, String description, String url, boolean hasDivider) {
        var c = new TeamView(this);
        c.setName(name);
        c.setDescription(description);
        c.setImageURL(url + ".png");
        c.setURL(url);
        c.setHasDivider(hasDivider);
        binding.team.addView(c);
    }
    
    private void openURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}

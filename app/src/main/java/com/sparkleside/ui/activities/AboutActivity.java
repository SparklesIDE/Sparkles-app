package com.sparkleside.ui.activities;

import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.StringRes;

import com.bumptech.glide.Glide;

import com.sparkleside.R;
import com.sparkleside.databinding.ActivityAboutBinding;
import com.sparkleside.ui.components.TeamMemberView;

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
		configureTeamMembers();
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
            "Idk what should I say :/",
            binding.imgSyn
        );
        peekAndPop(
            "Yamen",
            "https://github.com/YamenHer.png",
            "A Kool Utilities maker",
            binding.imgYamen
        );
        peekAndPop(
            "Aquiles Trindade",
            "https://github.com/trindadedev13.png",
            "I use Compose btw",
            binding.imgTrindade
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
    
    private void configureTeamMembers() {
        TeamMember(
            "Hanzo",
            Role.DEVELOPER,
            "https://github.com/HanzoDev1375",
            true
        );
        
        TeamMember(
            "Thiarley Rocha",
            Role.DEVELOPER,
            "https://github.com/thdev-only",
            true
        );
        
        TeamMember(
            "Rohit Kushvaha",
            Role.DEVELOPER,
            "https://github.com/RohitKushvaha01",
            true
        );
        
        TeamMember(
            "Jaiel Lima Miranda",
            Role.TRANSLATOR,
            "https://github.com/jetrom17",
            false
        );
    }
    
    private void peekAndPop(
        String name,
        String imageUrl,
        String phrase,
        View v
    ) {
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
    
    private void TeamMember(
        String name,
        Role role,
        String url,
        boolean hasDivider
    ) {
        var c = new TeamMemberView(this);
        c.setName(name);
        c.setDescription(role.getName(this));
        c.setImageURL(url + ".png");
        c.setURL(url);
        c.setHasDivider(hasDivider);
        peekAndPop(
	    name,
	    url + ".png",
	    role.getName(this), // todo: custom phrase 
	    c.getRoot()
	);
        binding.team.addView(c);
    }
    
    private void openURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    
    public enum Role {
        TRANSLATOR(R.string.about_tag_translator),
        DEVELOPER(R.string.about_tag_developer);
        
        @StringRes private final int stringResId;
        
        Role(@StringRes int stringResId) {
            this.stringResId = stringResId;
        }
        
        public String getName(Context context) {
            return context.getString(stringResId);
        }
    }
}

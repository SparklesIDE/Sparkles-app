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

import com.peekandpop.shalskar.peekandpop.PeekAndPop;

import dev.trindadedev.ui_utils.UI;

import java.net.URI;

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
        UI.handleInsetts(binding.getRoot());
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
            "A Material You Lover Android/Web Developer",
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
            "I use Jetpack Compose, btw",
            binding.imgTrindade
        );
    }
    
    private void configureLinks() {
        binding.tg.setOnClickListener(v->{
            openURL("https://www.telegram.me/sparkleside");
        });
        
        binding.github.setOnClickListener(v->{
            openURL("https://github.com/sparkleside/sparkles-app");
        });
        
        binding.hanzo.setOnClickListener(v->{
            openURL("https://github.com/yamenher");
        });
        
        binding.syn.setOnClickListener(v->{
            openURL("https://github.com/syntaxspin");
        });
        
        binding.trindade.setOnClickListener(v->{
            openURL("https://github.com/trindadedev13");
        });
        
    }
    
    private void configureTeamMembers() {
        TeamMember(
            "Hanzo",
            Role.DEVELOPER,
            "https://github.com/HanzoDev1375",
            "Ghost",
            true
        );
        
        TeamMember(
            "Thiarley Rocha",
            Role.DEVELOPER,
            "https://github.com/thdev-only",
            "lets play minecraft?",
            true
        );
        
        TeamMember(
            "Rohit Kushvaha",
            Role.DEVELOPER,
            "https://github.com/RohitKushvaha01",
            "Idk",
            true
        );
        TeamMember(
	        "NEOAPPS",
	        Role.DEVELOPER,
	        "https://github.com/neoapps-dev",
	        "i use arch, btw",
	        true
	    );
        TeamMember(
            "Jaiel Lima Miranda",
            Role.TRANSLATOR,
            "https://github.com/jetrom17",
	        "Happy is the man that finds wisdom, and the man that gets understanding. \n \n Proverbs 3:13",
            true
        );
        TeamMember(
            "Alex",
            Role.TRANSLATOR,
            "https://github.com/paxsenix0",
	        "PaxSenix bootlegger",
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
        TextView peekTextName = peekAndPop.getPeekView().findViewById(R.id.name);
        peekTextName.setText(name);
        TextView peekTextPhrase = peekAndPop.getPeekView().findViewById(R.id.phrase);
        peekTextPhrase.setText(phrase);
    }
    
    private void TeamMember(
        String name,
        Role role,
        String url,
	String phrase,
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
	    phrase,
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

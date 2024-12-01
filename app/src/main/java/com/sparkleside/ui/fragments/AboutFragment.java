package com.sparkleside.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.sparkleside.R;
import com.sparkleside.databinding.FragmentAboutBinding;
import com.sparkleside.ui.components.TeamMemberView;

import java.util.ArrayList;
import java.util.List;

import dev.trindadedev.ui_utils.UI;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return FragmentAboutBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        var binding = FragmentAboutBinding.bind(view);

        configureDevelopers(binding);
        configureLinks(binding);
        getTeamMembers().forEach(binding.team::addView);
        UI.handleInsetts(binding.getRoot());
    }

    private void configureDevelopers(@NonNull FragmentAboutBinding binding) {
        Glide.with(this)
            .load("https://github.com/syntaxspins.png")
            .into(binding.imgSyn);
        Glide.with(this)
            .load("https://github.com/yamenher.png")
            .into(binding.imgYamen);
        Glide.with(this)
            .load("https://github.com/trindadedev13.png")
            .into(binding.imgTrindade);

        peekAndPop(
            "SyntaxSpin",
            "https://github.com/syntaxspins.png",
            getString(R.string.syntaxspin_phrase),
            binding.imgSyn
        );
        peekAndPop(
            "Yamen",
            "https://github.com/YamenHer.png",
            getString(R.string.yamenher_phrase),
            binding.imgYamen
        );
        peekAndPop(
            "Aquiles Trindade",
            "https://github.com/trindadedev13.png",
            getString(R.string.trindadedev_phrase),
            binding.imgTrindade
        );
    }

    private void configureLinks(@NonNull FragmentAboutBinding binding) {
        binding.tg.setOnClickListener(v -> {
            openURL("https://www.telegram.me/sparkleside");
        });

        binding.github.setOnClickListener(v -> {
            openURL("https://github.com/sparkleside/sparkles-app");
        });

        binding.hanzo.setOnClickListener(v -> {
            openURL("https://github.com/yamenher");
        });

        binding.syn.setOnClickListener(v -> {
            openURL("https://github.com/syntaxspins");
        });

        binding.trindade.setOnClickListener(v -> {
            openURL("https://github.com/trindadedev13");
        });

    }

    private List<TeamMemberView> getTeamMembers() {
        var list = new ArrayList<TeamMemberView>();
        list.add(teamMember(
            "Hanzo",
            Role.DEVELOPER,
            "https://github.com/HanzoDev1375",
            getString(R.string.hanzo_phrase),
            true
        ));

        list.add(teamMember(
            "Thiarley Rocha",
            Role.DEVELOPER,
            "https://github.com/thdev-only",
            getString(R.string.thiarley_rocha_phrase),
            true
        ));

        list.add(teamMember(
            "Rohit Kushvaha",
            Role.DEVELOPER,
            "https://github.com/RohitKushvaha01",
            getString(R.string.rohit_kushvaha_phrase),
            true
        ));

        list.add(teamMember(
            "NEOAPPS",
            Role.DEVELOPER,
            "https://github.com/neoapps-dev",
            getString(R.string.neoapps_phrase),
            true
        ));

        list.add(teamMember(
            "Jeiel Lima Miranda",
            Role.TRANSLATOR,
            "https://github.com/jetrom17",
            getString(R.string.jaiel_lima_phrase),
            true
        ));

        list.add(teamMember(
            "Alex",
            Role.TRANSLATOR,
            "https://github.com/paxsenix0",
            getString(R.string.alex_phrase),
            false
        ));

        return list;
    }

    private void peekAndPop(
        String name,
        String imageUrl,
        String phrase,
        View v
    ) {
        PeekAndPop peekAndPop = new PeekAndPop.Builder(requireActivity())
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

    private TeamMemberView teamMember(
        String name,
        Role role,
        String url,
        String phrase,
        boolean hasDivider
    ) {
        var c = new TeamMemberView(requireActivity());
        c.setName(name);
        c.setDescription(role.getName(requireActivity()));
        c.setImageURL(url + ".png");
        c.setURL(url);
        c.setHasDivider(hasDivider);
        peekAndPop(
            name,
            url + ".png",
            phrase,
            c.getRoot()
        );

        return c;
    }

    private void openURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public enum Role {
        TRANSLATOR(R.string.about_tag_translator),
        DEVELOPER(R.string.about_tag_developer);

        @StringRes
        private final int stringResId;

        Role(@StringRes int stringResId) {
            this.stringResId = stringResId;
        }

        @NonNull
        public String getName(@NonNull Context context) {
            return context.getString(stringResId);
        }
    }
}

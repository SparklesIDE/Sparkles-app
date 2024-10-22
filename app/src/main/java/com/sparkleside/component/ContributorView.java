package com.sparkleside.component;

import android.net.Uri;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import com.google.android.material.imageview.ShapeableImageView;

import com.sparkleside.databinding.LayoutContributorViewBinding; 

public class ContributorView extends RelativeLayout {

    private LayoutContributorViewBinding binding;
    private String url;
    private Context context;
    private boolean hasDivider;

    public ContributorView(Context context) {
        super(context);
        init(context);
    }

    public ContributorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContributorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        binding = LayoutContributorViewBinding.inflate(LayoutInflater.from(context), this, true);
        binding.getRoot().setOnClickListener(v -> {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getURL()));
                context.startActivity(i);
            } catch(Exception e) {
                // nah
            }
        });
    }

    public void setImageURL(String url) {
        Glide
            .with(context)
            .load(url)
            .into(binding.avatar);
    }

    public void setName(String name) {
        binding.name.setText(name);
    }

    public void setDescription(String description) {
        binding.description.setText(description);
    }
    
    public void setURL(String url) {
        this.url= url;
    }
    
    public String getURL() {
        return url;
    }
    
    public void setHasDivider(boolean hasDivider) {
        this.hasDivider = hasDivider;
    }
}
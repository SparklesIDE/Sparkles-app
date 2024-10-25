package dev.trindadedev.ui_utils.preferences.withicon;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import dev.trindadedev.ui_utils.R;

public class Preference extends LinearLayout {

    public TextView preferenceTitle;
    public TextView preferenceDescription;
    public ImageView preferenceIcon;
    public View preference;

    public Preference(Context context) {
        this(context, null);
    }

    public Preference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Preference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_preference_withicon, this, true);

        preferenceTitle = findViewById(R.id.preference_title);
        preferenceDescription = findViewById(R.id.preference_description);
        preferenceIcon = findViewById(R.id.preference_icon);
        preference = findViewById(R.id.preference);

        if (attrs != null) {
            obtainStyledAttributes(context, attrs);
        }
    }

    private void obtainStyledAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Preference,
                0, 0
        );

        try {
            String title = typedArray.getString(R.styleable.Preference_preferenceTitle);
            String description = typedArray.getString(R.styleable.Preference_preferenceDescription);
            int iconResId = typedArray.getResourceId(R.styleable.Preference_preferenceIcon, 0);

            preferenceTitle.setText(title != null ? title : "");
            preferenceDescription.setText(description != null ? description : "");

            if (iconResId != 0) {
                preferenceIcon.setImageResource(iconResId);
            } else {
                preferenceIcon.setVisibility(View.GONE);
            }
        } finally {
            typedArray.recycle();
        }
    }

    public void setPreferenceClickListener(OnClickListener listenerClick) {
        preference.setOnClickListener(listenerClick);
    }

    public void setTitle(String value) {
        preferenceTitle.setText(value);
    }

    public void setDescription(String value) {
        preferenceDescription.setText(value);
    }

    public void setIcon(@DrawableRes int resId) {
        if (resId != 0) {
            preferenceIcon.setImageResource(resId);
            preferenceIcon.setVisibility(View.VISIBLE);
        } else {
            preferenceIcon.setVisibility(View.GONE);
        }
    }
}
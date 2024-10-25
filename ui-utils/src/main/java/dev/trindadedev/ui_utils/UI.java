package dev.trindadedev.ui_utils;

import android.view.View;

import androidx.core.view.WindowInsetsCompat;

import dev.chrisbanes.insetter.Insetter;

public class UI {

    public static void handleInsetts(View rootView) {
        Insetter
            .builder()
            .padding(WindowInsetsCompat.Type.navigationBars())
            .applyToView(rootView);
    }
}
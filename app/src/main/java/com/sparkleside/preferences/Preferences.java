package com.sparkleside.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

import com.sparkleside.BuildConfig;

/*
 * Basic class to manage preferences
 * TODO: use DataStore instead Preferences
 * @author Aquiles Trindade (trindadedev)
 */

public class Preferences {
    /*
     * Preferences related with theme
     */
    public static class Theme {
        private static final String THEME_PREFERENCE = BuildConfig.APPLICATION_ID + ".theme_prefs";
        private static final String THEME_MODE_KEY = "THEME_MODE";
        private static final String THEME_MONET_KEY = "THEME_MONET";

        public static int getThemeMode(Context ctx) {
            var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
            return sp.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

        public static boolean isMonetEnable(Context ctx) {
            var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
            return sp.getBoolean(THEME_MONET_KEY, false);
        }

        public static void setThemeMode(Context ctx, int themeMode) {
            AppCompatDelegate.setDefaultNightMode(themeMode);
            var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
            var spEditor = sp.edit();
            spEditor.putInt(THEME_MODE_KEY, themeMode);
            spEditor.apply();
        }

        public static void setMonetEnable(Context ctx, boolean isMonetEnable) {
            var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
            var spEditor = sp.edit();
            spEditor.putBoolean(THEME_MONET_KEY, isMonetEnable);
            spEditor.apply();
        }
    }
}

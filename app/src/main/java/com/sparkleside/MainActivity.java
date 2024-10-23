package com.sparkleside;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sparkleside.component.ExpandableLayout;
import com.sparkleside.databinding.ActivityMainBinding;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.toolbox.setExpansion(false);
        binding.toolbox.setDuration(200);
        binding.toolbox.setOrientatin(ExpandableLayout.VERTICAL);

        if (Build.VERSION.SDK_INT >= 26) {
            binding.term.setTooltipText(getString(R.string.tooltip_terminal));
            binding.search.setTooltipText(getString(R.string.tooltip_search));
            binding.file.setTooltipText(getString(R.string.tooltip_new_file));
            binding.settings.setTooltipText(getString(R.string.tooltip_settings));
        }

        binding.fab.setOnClickListener(v -> 
            Toast.makeText(MainActivity.this, "ComingSoon", Toast.LENGTH_SHORT).show()
        );

        binding.settings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        binding.editor.setTypefaceText(
            Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmono.ttf")
        );

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        var scheme = binding.editor.getColorScheme();

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            scheme.setColor(EditorColorScheme.WHOLE_BACKGROUND,
                ContextCompat.getColor(this, R.color.md_theme_surface));
            scheme.setColor(EditorColorScheme.CURRENT_LINE,
                ContextCompat.getColor(this, R.color.md_theme_surfaceBright));
            scheme.setColor(EditorColorScheme.LINE_NUMBER_PANEL,
                ContextCompat.getColor(this, R.color.md_theme_surface));
            scheme.setColor(EditorColorScheme.LINE_NUMBER_BACKGROUND,
                ContextCompat.getColor(this, R.color.md_theme_surface));
            scheme.setColor(EditorColorScheme.KEYWORD, 0xFF42BE6A);
            scheme.setColor(EditorColorScheme.FUNCTION_NAME, 0xFF62DE8A);
            scheme.setColor(EditorColorScheme.TEXT_NORMAL, 0xFFA2D2A9);
            scheme.setColor(EditorColorScheme.OPERATOR, 0xFFDDE5DB);
        } else {
            scheme.setColor(EditorColorScheme.WHOLE_BACKGROUND,
                ContextCompat.getColor(this, R.color.md_theme_surface));
            scheme.setColor(EditorColorScheme.CURRENT_LINE,
                ContextCompat.getColor(this, R.color.md_theme_surfaceDim));
            scheme.setColor(EditorColorScheme.LINE_NUMBER_PANEL,
                ContextCompat.getColor(this, R.color.md_theme_surface));
            scheme.setColor(EditorColorScheme.LINE_NUMBER_BACKGROUND,
                ContextCompat.getColor(this, R.color.md_theme_surface));
            scheme.setColor(EditorColorScheme.KEYWORD, 0xFF42BE6A);
            scheme.setColor(EditorColorScheme.FUNCTION_NAME, 0xFF62DE8A);
            scheme.setColor(EditorColorScheme.TEXT_NORMAL, 0xFF629269);
            scheme.setColor(EditorColorScheme.OPERATOR, 0xFFDDE5DB);
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.fab, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
            mlp.bottomMargin = insets.bottom;
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });

        binding.term.setOnClickListener(it -> 
            startActivity(new Intent(getApplicationContext(), TermuxActivityCompat.class))
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_more) {
            if (!binding.toolbox.isExpanded()) {
                binding.toolbox.expand();
            } else {
                binding.toolbox.collapse();
            }
            return true;
        }
        if (id == R.id.menu_undo) {
            binding.editor.undo();
            return true;
        }
        if (id == R.id.menu_redo) {
            binding.editor.redo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
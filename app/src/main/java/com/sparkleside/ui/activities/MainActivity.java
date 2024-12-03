package com.sparkleside.ui.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.sidesheet.SideSheetDialog;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.sparkleside.R;
import dev.trindadedev.compiler.java.JavaCompiler;
import dev.trindadedev.compiler.java.JavaCompiler.CompileItem;
import com.sparkleside.databinding.ActivityMainBinding;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.ui.components.ExpandableLayout;
import com.sparkleside.ui.components.executorservice.FileOperationExecutor;
import com.sparkleside.ui.editor.schemes.SparklesScheme;
import com.zyron.filetree.provider.FileTreeIconProvider;
import com.zyron.filetree.widget.FileTreeView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class MainActivity extends BaseActivity {

  private ActivityMainBinding binding;
  private FileTreeIconProvider fileIconProvider;
  private FileOperationExecutor fileoperate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    MaterialSharedAxis exitTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    exitTransition.addTarget(R.id.coordinator);
    getWindow().setExitTransition(exitTransition);

    MaterialSharedAxis reenterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    reenterTransition.addTarget(R.id.coordinator);
    getWindow().setReenterTransition(reenterTransition);

    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);

    binding.toolbar.setNavigationIcon(R.drawable.menu_24px);

    binding.toolbar.setNavigationOnClickListener(view -> {
      SideSheetDialog sideSheetDialog = new SideSheetDialog(MainActivity.this);
      sideSheetDialog.setContentView(R.layout.toolbox_sidesheet); // Set content first
      sideSheetDialog.setSheetEdge(Gravity.START); // Then set the sheet edge

      Window window = sideSheetDialog.getWindow();
      if (window != null) {
        window.setDimAmount(0.4f);
      }

      MaterialButton materialButton = sideSheetDialog.findViewById(R.id.materialbutton);
      BottomNavigationView bottomNav = sideSheetDialog.findViewById(R.id.navside);
      LinearLayout filetreecon = sideSheetDialog.findViewById(R.id.FileTreeCon);
      LinearLayout gitcon = sideSheetDialog.findViewById(R.id.GitCon);
      NavigationView navview = sideSheetDialog.findViewById(R.id.navview);
      FrameLayout container = sideSheetDialog.findViewById(R.id.container);
      FileTreeView fileTree = sideSheetDialog.findViewById(R.id.file_tree_view);
      fileTree.initializeFileTree("/storage/emulated/0", fileoperate , fileIconProvider);

      gitcon.setVisibility(View.GONE);
      navview.setVisibility(View.GONE);
      filetreecon.setVisibility(View.VISIBLE);
      
      bottomNav.setOnNavigationItemSelectedListener(item -> {
        var sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        TransitionManager.beginDelayedTransition(container, sharedAxis);
    
        if (item.getItemId() == R.id.file) {
          gitcon.setVisibility(View.GONE);
          navview.setVisibility(View.GONE);
          filetreecon.setVisibility(View.VISIBLE);
        } else if (item.getItemId() == R.id.git) {
          filetreecon.setVisibility(View.GONE);
          navview.setVisibility(View.GONE);
          gitcon.setVisibility(View.VISIBLE);
         } else if (item.getItemId() == R.id.toolboxm) {
          filetreecon.setVisibility(View.GONE);
          gitcon.setVisibility(View.GONE);
          navview.setVisibility(View.VISIBLE);
        }

        return true;
      });
      
      if (materialButton != null) {
        materialButton.setOnClickListener(v -> sideSheetDialog.hide());
      }

      sideSheetDialog.show();
    });


    binding.toolbox.setExpansion(true);
    binding.toolbox.setDuration(200);
    binding.toolbox.setOrientatin(ExpandableLayout.VERTICAL);

    if (Build.VERSION.SDK_INT >= 26) {
      binding.term.setTooltipText(getString(R.string.tooltip_terminal));
      binding.search.setTooltipText(getString(R.string.tooltip_search));
      binding.file.setTooltipText(getString(R.string.tooltip_new_file));
      binding.settings.setTooltipText(getString(R.string.tooltip_settings));
    }

    binding.fab.setOnClickListener(v -> compileJavaCode());
    binding.term.setOnClickListener(v -> startActivity(new Intent(this, TerminalActivity.class)));

    binding.settings.setOnClickListener(
        v -> {
          Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
          ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(this);
          startActivity(intent, optionsCompat.toBundle());
        });

    binding.editor.setTypefaceText(
        Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmono.ttf"));

    int currentNightMode =
        getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    SparklesScheme scheme = new SparklesScheme(binding.editor);
    scheme.apply();

    ViewCompat.setOnApplyWindowInsetsListener(
        binding.fab,
        (v, windowInsets) -> {
          Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
          MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
          mlp.bottomMargin = insets.bottom + 72;
          v.setLayoutParams(mlp);
          return WindowInsetsCompat.CONSUMED;
        });
  }

  private void compileJavaCode() {
    var compiler = new JavaCompiler(this);
    var path = "SparklesIDE/temp/";
    var javaFile = new File(Environment.getExternalStorageDirectory(), path + "Main.java");
    var javaCode = binding.editor.getText().toString();
    var parentDir = javaFile.getParentFile();
    var outputDir = new File(Environment.getExternalStorageDirectory(), path);
    var logs = new StringBuilder();
    
    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs();
    }

    try (FileOutputStream fos = new FileOutputStream(javaFile)) {
      fos.write(javaCode.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    compiler.compile(new CompileItem(javaFile, outputDir));

    for (var log : compiler.getLogs()) {
      logs.append(log);
    }
    logs.append(compiler.getJavaVersion());

    new MaterialAlertDialogBuilder(this)
        .setTitle(getString(R.string.common_word_result))
        .setMessage(a.toString())
        .setPositiveButton(getString(R.string.common_word_ok), (d, w) -> d.dismiss())
        .setNeutralButton(getString(R.string.common_word_copy), (d, w) -> copyText(a.toString()))
        .show();
  }
  
  private void copyText(final String a) {
    var clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    var clip = ClipData.newPlainText("Result", a);
    clipboard.setPrimaryClip(clip);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
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

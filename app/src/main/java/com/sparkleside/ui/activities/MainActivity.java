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
import com.sparkleside.databinding.ToolboxSidesheetBinding;
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
  private SideSheetDialog sideSheetDialog;
  private ToolboxSidesheetBinding sheetBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    MaterialSharedAxis exitTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    exitTransition.addTarget(R.id.coordinator);
    getWindow().setExitTransition(exitTransition);

    var reenterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    reenterTransition.addTarget(R.id.coordinator);
    getWindow().setReenterTransition(reenterTransition);

    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);

    binding.toolbar.setNavigationIcon(R.drawable.menu_24px);

    binding.toolbar.setNavigationOnClickListener(view -> {
      getSideSheet().show();
    });

    binding.options.setExpansion(true);
    binding.options.setDuration(200);
    binding.options.setOrientatin(ExpandableLayout.VERTICAL);

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

    var currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
    var scheme = new SparklesScheme(binding.editor);
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
  
  private final SideSheetDialog getSideSheet() {
    sideSheetDialog = new SideSheetDialog(MainActivity.this);
    sheetBinding = ToolboxSidesheetBinding.inflate(getLayoutInflater());
    
    sideSheetDialog.setContentView(sheetBinding.getRoot());
    sideSheetDialog.setSheetEdge(Gravity.START);

    var window = sideSheetDialog.getWindow();
    if (window != null) {
      window.setDimAmount(0.4f);
    }

    sheetBinding.fileTree.initializeFileTree("/storage/emulated/0", fileoperate , fileIconProvider);
    
    sheetBinding.contentGit.setVisibility(View.GONE);
    sheetBinding.contentToolbox.setVisibility(View.GONE);
    sheetBinding.contentFileTree.setVisibility(View.VISIBLE);
      
    sheetBinding.bottomNav.setOnNavigationItemSelectedListener(item -> {
      var sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.X, true);
      TransitionManager.beginDelayedTransition(sheetBinding.container, sharedAxis);
    
      if (item.getItemId() == R.id.option_file_tree) {
        sheetBinding.contentGit.setVisibility(View.GONE);
        sheetBinding.contentToolbox.setVisibility(View.GONE);
        sheetBinding.contentFileTree.setVisibility(View.VISIBLE);
      } else if (item.getItemId() == R.id.option_git) {
        sheetBinding.contentGit.setVisibility(View.VISIBLE);
        sheetBinding.contentToolbox.setVisibility(View.GONE);
        sheetBinding.contentFileTree.setVisibility(View.GONE);
      } else if (item.getItemId() == R.id.option_toolbox) {
        sheetBinding.contentGit.setVisibility(View.GONE);
        sheetBinding.contentToolbox.setVisibility(View.VISIBLE);
        sheetBinding.contentFileTree.setVisibility(View.GONE);
      }
      return true;
    });
      
    sheetBinding.hide.setOnClickListener(v -> sideSheetDialog.hide());
    
    return sideSheetDialog;
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
        .setMessage(logs.toString())
        .setPositiveButton(getString(R.string.common_word_ok), (d, w) -> d.dismiss())
        .setNeutralButton(getString(R.string.common_word_copy), (d, w) -> copyText(logs.toString()))
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
      if (!binding.options.isExpanded()) {
        binding.options.expand();
      } else {
        binding.options.collapse();
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

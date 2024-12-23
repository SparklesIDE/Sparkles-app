package com.sparkleside.ui.activities;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.google.android.material.sidesheet.SideSheetDialog;
import com.google.android.material.transition.platform.MaterialSharedAxis;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityMainBinding;
//import com.sparkleside.databinding.ToolboxSidebinding;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.ui.components.ExpandableLayout;
import com.sparkleside.ui.components.executorservice.FileOperationExecutor;
import com.sparkleside.ui.editor.schemes.SparklesScheme;
import com.zyron.filetree.provider.FileTreeIconProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.robok.engine.feature.compiler.java.JavaCompiler;
import org.robok.engine.feature.compiler.java.JavaCompiler.CompileItem;
import com.google.android.material.transition.platform.MaterialContainerTransform;

public class MainActivity extends BaseActivity {

  private ActivityMainBinding binding;
  private FileTreeIconProvider fileIconProvider;
  private FileOperationExecutor fileoperate;
  private SideSheetDialog sideSheetDialog;
    

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
    
    /*Permission Controller by Rakhmonov Bobur*/
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { 
     if (!Environment.isExternalStorageManager()) { 
     MaterialAlertDialogBuilder perm = new MaterialAlertDialogBuilder(this);
     LayoutInflater permview= getLayoutInflater();
     View perview = (View) permview.inflate(R.layout.dialogpermission, null);
     perm.setView(perview);
     final TextView positive = (TextView)
     perview.findViewById(android.R.id.button1);
     final TextView negative = (TextView)
     perview.findViewById(android.R.id.button3);
     positive.setOnClickListener(v -> {
     Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
   intent.setData(Uri.parse("package:" + getPackageName()));
   startActivity(intent);
     });
     negative.setOnClickListener(v ->{ finishAffinity();});
     perm.setCancelable(false);
     perm.create().show();           
     
     
  
     } 
      }
    
    int statusBarHeight = getResources().getDimensionPixelSize(
    getResources().getIdentifier("status_bar_height", "dimen", "android"));
    int navigationBarHeight = getResources().getDimensionPixelSize(
    getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
     ViewGroup.LayoutParams layoutParams = binding.navigationView.getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
    marginLayoutParams.topMargin = statusBarHeight ; // Set top margin in pixels
   // marginLayoutParams.bottomMargin = navigationBarHeight ; // Set bottom margin in pixels
    binding.navigationView.setLayoutParams(marginLayoutParams);
}
    
        
   
        binding.drawer.setScrimColor(Color.TRANSPARENT);
    binding.drawer.setDrawerElevation(0f);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawer, R.string.app_name, R.string.app_name) { 
        @Override public void onDrawerSlide(View drawerView, float slideOffset) {
             super.onDrawerSlide(drawerView, slideOffset);
                 float slideX = drawerView.getWidth() * slideOffset; binding.coordinator.setTranslationX(slideX); 
            }
             }; 
    binding.drawer.addDrawerListener(toggle);    
    binding.drawer.setFitsSystemWindows(false);
    
    binding.fileTreeView.initializeFileTree(
        "/storage/emulated/0", fileoperate, fileIconProvider);

    binding.contentGit.setVisibility(View.GONE);
    binding.contentToolbox.setVisibility(View.GONE);
    binding.contentFileTree.setVisibility(View.VISIBLE);

    binding.btmOptions.setOnNavigationItemSelectedListener(
        item -> {
          var sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.X, true);
          TransitionManager.beginDelayedTransition(binding.container, sharedAxis);

          if (item.getItemId() == R.id.option_file_tree) {
            binding.contentGit.setVisibility(View.GONE);
            binding.contentToolbox.setVisibility(View.GONE);
            binding.contentFileTree.setVisibility(View.VISIBLE);
          } else if (item.getItemId() == R.id.option_git) {
            binding.contentGit.setVisibility(View.VISIBLE);
            binding.contentToolbox.setVisibility(View.GONE);
            binding.contentFileTree.setVisibility(View.GONE);
          } else if (item.getItemId() == R.id.option_toolbox) {
            binding.contentGit.setVisibility(View.GONE);
            binding.contentToolbox.setVisibility(View.VISIBLE);
            binding.contentFileTree.setVisibility(View.GONE);
          }
          return true;
        });

    binding.hide.setOnClickListener(v -> {
    if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
      binding.drawer.closeDrawer(GravityCompat.START);
    }
    });
    binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    
    binding.toolbar.setNavigationIcon(R.drawable.menu_24px);
    binding.toolbar.setNavigationOnClickListener(v -> {
    if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
        binding.drawer.closeDrawer(GravityCompat.START);
      } else {
        binding.drawer.openDrawer(GravityCompat.START);
      }
    
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

    binding.fab.setOnClickListener(v -> fabCompiler());
    binding.term.setOnClickListener(v -> startActivity(new Intent(this, TerminalActivity.class)));

    binding.settings.setOnClickListener(
        v -> {
          Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
          ActivityOptions optionsCompat = ActivityOptions.makeSceneTransitionAnimation(this);
          startActivity(intent, optionsCompat.toBundle());
        });

    binding.editor.setTypefaceText(
        Typeface.createFromAsset(getAssets(), "fonts/jetbrainsmono.ttf"));

    var currentNightMode =
        getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
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
        ViewCompat.setOnApplyWindowInsetsListener(
        binding.compilersCard,
        (v, windowInsets) -> {
          Insets insetss = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
          MarginLayoutParams mlp2 = (MarginLayoutParams) v.getLayoutParams();
          mlp2.bottomMargin = insetss.bottom + 72;
          v.setLayoutParams(mlp2);
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
   @Override
	public void onBackPressed() {
		if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
			binding.drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
   
    public void fabCompiler(){
    MaterialContainerTransform transition = buildContainerTransform(true);
    transition.setStartView(binding.fab);
    transition.setEndView(binding.compilersCard);
    transition.addTarget(binding.compilersCard);
    // Trigger the container transform transition.
    TransitionManager.beginDelayedTransition(binding.coordinator, transition);
    binding.fab.setVisibility(View.INVISIBLE);
    binding.compilersCard.setVisibility(View.VISIBLE);
    //Listeners
    binding.close.setOnClickListener(v->{
    MaterialContainerTransform transition2 = buildContainerTransform(false);
    transition2.setStartView(binding.compilersCard);
    transition2.setEndView(binding.fab);
    transition2.addTarget(binding.fab);    
    TransitionManager.beginDelayedTransition(binding.coordinator, transition2);
    binding.fab.setVisibility(View.VISIBLE);
    binding.compilersCard.setVisibility(View.INVISIBLE);
    });
    binding.java.setOnClickListener(v-> compileJavaCode());
    } 
    
     private MaterialContainerTransform buildContainerTransform(boolean entering) {
     MaterialContainerTransform transform = new MaterialContainerTransform(MainActivity.this, entering);
     transform.setScrimColor(Color.TRANSPARENT);
     transform.setDrawingViewId(binding.coordinator.getId());
      return transform;
     }
  
}

package com.sparkleseditor.android;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.*;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.*;
import com.sparkleseditor.android.databinding.ActivityMainBinding;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
   final com.google.android.material.bottomsheet.BottomSheetDialog btmSheet = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
	View convertView;
	convertView = getLayoutInflater().inflate(R.layout.result, null);
	btmSheet.setContentView(convertView);
	btmSheet.setCancelable(true);
                    btmSheet.show();
            }
        });
        
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_editor, R.id.nav_ai, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   @Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId())
    {
    case R.id.action_settings:
           final AlertDialog dial = new AlertDialog.Builder(MainActivity.this).create();
View view = getLayoutInflater().inflate(R.layout.dialog, null);
dial.getWindow().getDecorView().setBackgroundColor(0);
dial.setView(view);
dial.setCancelable(true);

final LinearLayout content_dialog = view.findViewById(R.id.content_dialog);
final ImageView dialog_icon = view.findViewById(R.id.dialog_icon);
final TextView dialog_text = view.findViewById(R.id.dialog_text);
final LinearLayout button_allow = view.findViewById(R.id.button_allow);
final LinearLayout button_deny = view.findViewById(R.id.button_deny);
final TextView button1_text = view.findViewById(R.id.button1_text);
final TextView button2_text = view.findViewById(R.id.button2_text);
dialog_text.setText(Html.fromHtml(_text));
dialog_icon.setImageResource(getResources().getIdentifier(_icon, "drawable", getPackageName()));
dialog_text.setTextColor(Color.parseColor(_txt_color));
button1_text.setTextColor(Color.parseColor(button_text_colors));
button2_text.setTextColor(Color.parseColor(button_text_colors));
android.graphics.drawable.GradientDrawable trindade_content_dialog = new android.graphics.drawable.GradientDrawable();
trindade_content_dialog.setColor(Color.parseColor(_bg_color));
trindade_content_dialog.setCornerRadii(new float[] { 50, 50, 50, 50, 50, 50, 50, 50 });
android.content.res.ColorStateList cv1content_dialog = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{0xFFFFFFFF});

android.graphics.drawable.RippleDrawable dw1content_dialog = new android.graphics.drawable.RippleDrawable(cv1content_dialog, trindade_content_dialog, null);

content_dialog.setBackground(dw1content_dialog);
android.graphics.drawable.GradientDrawable trindade_button_allow = new android.graphics.drawable.GradientDrawable();
trindade_button_allow.setColor(Color.parseColor(_btns_color));
trindade_button_allow.setCornerRadii(new float[] { 20, 20, 20, 20, 10, 10, 10, 10 });
android.content.res.ColorStateList cv1button_allow = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{0xFFFFFFFF});

android.graphics.drawable.RippleDrawable dw1button_allow = new android.graphics.drawable.RippleDrawable(cv1button_allow, trindade_button_allow, null);

button_allow.setBackground(dw1button_allow);
android.graphics.drawable.GradientDrawable trindade_button_deny = new android.graphics.drawable.GradientDrawable();
trindade_button_deny.setColor(Color.parseColor(_btns_color));
trindade_button_deny.setCornerRadii(new float[] { 10, 10, 10, 10, 20, 20, 20, 20 });
android.content.res.ColorStateList cv1button_deny = new android.content.res.ColorStateList(new int[][]{new int[]{}},new int[]{0xFFFFFFFF});

android.graphics.drawable.RippleDrawable dw1button_deny = new android.graphics.drawable.RippleDrawable(cv1button_deny, trindade_button_deny, null);

button_deny.setBackground(dw1button_deny);
button_allow.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View _view) {
				 
		}
});
button_deny.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View _view) {
				 
		}
});

dial.show();
        break;
    
    }
    return true;
}

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
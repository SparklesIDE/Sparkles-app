package com.sparkleside.ui.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.rk.xededitor.terminal.virtualkeys.SpecialButton;
import com.rk.xededitor.terminal.virtualkeys.VirtualKeyButton;
import com.rk.xededitor.terminal.virtualkeys.VirtualKeysConstants;
import com.rk.xededitor.terminal.virtualkeys.VirtualKeysView;

import com.sparkleside.ui.components.TermuxActivityRootView;
import com.termux.view.TerminalView;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalSessionClient;
import com.termux.view.TerminalViewClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.json.JSONException;

public class TermuxActivityCompat extends AppCompatActivity implements TerminalViewClient {

  private HashMap<String, Object> imap = new HashMap<>();
  private String pos = "";
  private boolean mIsVisible;
  private TerminalView terminals;
  private static final String KEY_FONT_SIZE = "terminal_fontSize";
  private int MIN_FONT_SIZE;
  private int MAX_FONT_SIZE;
  private int DEFAULT_FONT_SIZE;
  private float terminalTextSize = 24f;
  private final float minTextSize = 10.0f;
  private final float maxTextSize = 40.0f;
  private TermuxActivityRootView rootview;

  @Override
  protected void onCreate(Bundle _savedInstanceState) {
    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    super.onCreate(_savedInstanceState);
    setContentView(R.layout.terminal);
    initialize(_savedInstanceState);
    initializeLogic();
  }

  private void initialize(Bundle _savedInstanceState) {
    terminals = findViewById(R.id.term);
    rootview = findViewById(R.id.coordinator);
    rootview.setActivity(this);
    rootview.setOnApplyWindowInsetsListener(new TermuxActivityRootView.WindowInsetsListener());
  }

  public boolean isVisible() {
    return mIsVisible;
  }

  public View getTermuxActivityBottomSpaceView() {
    return null;
  }

  private void initializeLogic() {

    String shell = "/bin/sh";
    if (!new File("/bin/sh").exists()) {
      shell = "/system/bin/sh";
    }
    String[] envVars = null;
    String[] argsList = {};
    String dsb = terminals.getContext().getFilesDir().getAbsolutePath();
    terminals.setTerminalViewClient(this);
    getWindow()
        .setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    TerminalSession terminalSession =
        new TerminalSession(
            shell,
            dsb,
            argsList,
            envVars,
            TerminalEmulator.DEFAULT_TERMINAL_TRANSCRIPT_ROWS,
            new TerminalSessionClient() {
              @Override
              public void onTextChanged(TerminalSession arg0) {
                terminals.onScreenUpdated();
              }

              @Override
              public void onTitleChanged(TerminalSession terminalSession) {}

              @Override
              public void onSessionFinished(TerminalSession terminalSession) {
                if (!terminalSession.isRunning()) {
                  terminalSession.finishIfRunning();
                  finish();
                }
              }

              @Override
              public void onBell(TerminalSession arg0) {}

              @Override
              public void onColorsChanged(TerminalSession session) {}

              @Override
              public void onTerminalCursorStateChange(boolean start) {}

              @Override
              public Integer getTerminalCursorStyle() {
                return TerminalEmulator.DEFAULT_TERMINAL_CURSOR_STYLE;
              }

              @Override
              public void logError(String arg0, String arg1) {}

              @Override
              public void logWarn(String arg0, String arg1) {}

              @Override
              public void logInfo(String arg0, String arg1) {}

              @Override
              public void logDebug(String arg0, String arg1) {}

              @Override
              public void logVerbose(String arg0, String arg1) {}

              @Override
              public void logStackTraceWithMessage(String arg0, String arg1, Exception arg2) {}

              @Override
              public void logStackTrace(String arg0, Exception arg1) {}

              @Override
              public void onPasteTextFromClipboard(TerminalSession session) {
                String clips = ClipboardUtils.getText().toString();
                if (clips.trim().length() > 0 && terminals != null && terminals.mEmulator != null) {
                  terminals.mEmulator.paste(clips);
                }
              }

              @Override
              public void onCopyTextToClipboard(TerminalSession session, String text) {
                ClipboardUtils.copyText(text);
              }

              @Override
              public void setTerminalShellPid(TerminalSession session, int pid) {}
            });

    if (terminalSession != null) {
      terminals.attachSession(terminalSession);
    }
    terminals.setTextSize(SizeUtils.dp2px(12f));
    terminals.setKeepScreenOn(true);
    terminalSession.titleChanged("1", shell);
    terminals.mTermSession.write(shell);
  }

  @Override
  public float onScale(float scale) {
    float currentTextSize = terminalTextSize;
    float newTextSize = currentTextSize * scale;

    newTextSize = Math.max(minTextSize, Math.min(newTextSize, maxTextSize));
    terminals.setTextSize((int) newTextSize);
    terminalTextSize = newTextSize;

    if (scale < 0.9f || scale > 1.1f) {
      return 1.0f;
    }
    return scale;
  }

  @Override
  public boolean isTerminalViewSelected() {
    return false;
  }

  @Override
  public boolean readShiftKey() {
    return false;
  }

  @Override
  public boolean readFnKey() {
    return false;
  }

  @Override
  public void onSingleTapUp(MotionEvent arg0) {
    terminals.setFocusable(true);
    terminals.setFocusableInTouchMode(true);
    terminals.requestFocus();
    KeyboardUtils.showSoftInput(terminals);
  }

  @Override
  public boolean shouldBackButtonBeMappedToEscape() {
    return false;
  }

  @Override
  public boolean shouldEnforceCharBasedInput() {
    return false;
  }

  @Override
  public boolean shouldUseCtrlSpaceWorkaround() {
    return false;
  }

  @Override
  public void copyModeChanged(boolean arg0) {}

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent e, TerminalSession session) {
    if (keyCode == KeyEvent.KEYCODE_ENTER && !session.isRunning()) {
      System.exit(1);
      return true;
    }
    return false;
  }

  @Override
  public boolean onKeyUp(int arg0, KeyEvent arg1) {
    return false;
  }

  @Override
  public boolean onLongPress(MotionEvent arg0) {
    return false;
  }

  @Override
  public boolean readControlKey() {

    return false;
  }

  @Override
  public boolean readAltKey() {

    return true;
  }

  @Override
  public boolean onCodePoint(int arg0, boolean arg1, TerminalSession arg2) {
    return false;
  }

  @Override
  public void onEmulatorSet() {}

  @Override
  public void logError(String arg0, String arg1) {}

  @Override
  public void logWarn(String arg0, String arg1) {}

  @Override
  public void logInfo(String arg0, String arg1) {}

  @Override
  public void logDebug(String arg0, String arg1) {}

  @Override
  public void logVerbose(String arg0, String arg1) {}

  @Override
  public void logStackTraceWithMessage(String arg0, String arg1, Exception arg2) {}

  @Override
  public void logStackTrace(String arg0, Exception arg1) {}

  public static final String VIRTUAL_KEYS =
      "["
          + "\n  ["
          + "\n    \"ESC\","
          + "\n    {"
          + "\n      \"key\": \"/\","
          + "\n      \"popup\": \"\\\\\""
          + "\n    },"
          + "\n    {"
          + "\n      \"key\": \"-\","
          + "\n      \"popup\": \"|\""
          + "\n    },"
          + "\n    \"HOME\","
          + "\n    \"UP\","
          + "\n    \"END\","
          + "\n    \"PGUP\""
          + "\n  ],"
          + "\n  ["
          + "\n    \"TAB\","
          + "\n    \"CTRL\","
          + "\n    \"ALT\","
          + "\n    \"LEFT\","
          + "\n    \"DOWN\","
          + "\n    \"RIGHT\","
          + "\n    \"PGDN\""
          + "\n  ]"
          + "\n]";

  private static final class KeyListener implements VirtualKeysView.IVirtualKeysView {

    private final TerminalView terminal;

    public KeyListener(TerminalView terminal) {
      this.terminal = terminal;
    }

    @Override
    public void onVirtualKeyButtonClick(View view, VirtualKeyButton buttonInfo, Button button) {
      if (terminal == null) {
        return;
      }
      if (buttonInfo.isMacro()) {
        String[] keys = buttonInfo.getKey().split(" ");
        boolean ctrlDown = false;
        boolean altDown = false;
        boolean shiftDown = false;
        boolean fnDown = false;
        for (String key : keys) {
          if (SpecialButton.CTRL.getKey().equals(key)) {
            ctrlDown = true;
          } else if (SpecialButton.ALT.getKey().equals(key)) {
            altDown = true;
          } else if (SpecialButton.SHIFT.getKey().equals(key)) {
            shiftDown = true;
          } else if (SpecialButton.FN.getKey().equals(key)) {
            fnDown = true;
          } else {
            onTerminalExtraKeyButtonClick(key, ctrlDown, altDown, shiftDown, fnDown);
            ctrlDown = false;
            altDown = false;
            shiftDown = false;
            fnDown = false;
          }
        }
      } else {
        onTerminalExtraKeyButtonClick(buttonInfo.getKey(), false, false, false, false);
      }
    }

    private void onTerminalExtraKeyButtonClick(
        String key, boolean ctrlDown, boolean altDown, boolean shiftDown, boolean fnDown) {
      if (VirtualKeysConstants.PRIMARY_KEY_CODES_FOR_STRINGS.containsKey(key)) {
        Integer keyCode = VirtualKeysConstants.PRIMARY_KEY_CODES_FOR_STRINGS.get(key);
        if (keyCode == null) {
          return;
        }
        int metaState = 0;
        if (ctrlDown) {
          metaState |= KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON;
        }
        if (altDown) {
          metaState |= KeyEvent.META_ALT_ON | KeyEvent.META_ALT_LEFT_ON;
        }
        if (shiftDown) {
          metaState |= KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_LEFT_ON;
        }
        if (fnDown) {
          metaState |= KeyEvent.META_FUNCTION_ON;
        }

        KeyEvent keyEvent = new KeyEvent(0, 0, KeyEvent.ACTION_UP, keyCode, 0, metaState);
        terminal.onKeyDown(keyCode, keyEvent);
      } else {
        // not a control char
        for (int off = 0; off < key.length(); ) {
          int codePoint = key.codePointAt(off);
          terminal.inputCodePoint(codePoint, 0, altDown, ctrlDown);
          off += Character.charCount(codePoint);
        }
      }
    }

    @Override
    public boolean performVirtualKeyButtonHapticFeedback(
        View view, VirtualKeyButton buttonInfo, Button button) {
      // No need to handle this
      // VirtualKeysView will take care of performing haptic feedback
      return false;
    }
  }
}

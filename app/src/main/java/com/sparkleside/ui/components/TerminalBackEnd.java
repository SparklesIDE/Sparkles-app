package com.sparkleside.ui.components;


import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.sparkleside.ui.activities.TerminalActivity;
import com.sparkleside.ui.components.virtualkeys.SpecialButton;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalSessionClient;
import com.termux.view.TerminalView;
import com.termux.view.TerminalViewClient;

/**
 * A basic implementation of termux's terminal
 *
 * @author Rohit Kushvaha (RohitKushvaha01).
 */
public class TerminalBackEnd implements TerminalViewClient, TerminalSessionClient {
    private int fontSize = SizeUtils.dp2px(24f);
    private TerminalView terminal;
    private final TerminalActivity activity;

    public TerminalBackEnd(TerminalActivity activity) {
        this.activity = activity;
    }

    public void setTerminal(TerminalView terminalView) {
        this.terminal = terminalView;
    }

    @Override
    public void onTextChanged(TerminalSession changedSession) {
        terminal.onScreenUpdated();
    }

    @Override
    public void onTitleChanged(TerminalSession changedSession) {}

    @Override
    public void onSessionFinished(TerminalSession finishedSession) {}

    @Override
    public void onCopyTextToClipboard(TerminalSession session, String text) {
        ClipboardUtils.copyText("Terminal", text);
    }

    @Override
    public void onPasteTextFromClipboard(TerminalSession session) {
        String clip = ClipboardUtils.getText().toString();
        if (!clip.trim().isEmpty() && terminal.mEmulator != null) {
            terminal.mEmulator.paste(clip);
        }
    }

    @Override
    public void onBell(TerminalSession session) {}

    @Override
    public void onColorsChanged(TerminalSession session) {}

    @Override
    public void onTerminalCursorStateChange(boolean state) {}

    @Override
    public Integer getTerminalCursorStyle() {
        return TerminalEmulator.DEFAULT_TERMINAL_CURSOR_STYLE;
    }

    @Override
    public void logError(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void logWarn(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void logInfo(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void logDebug(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void logVerbose(String tag, String message) {
        Log.v(tag, message);
    }

    @Override
    public void logStackTraceWithMessage(String tag, String message, Exception e) {
        Log.e(tag, message);
        if (e != null) {
            e.printStackTrace();
        }
    }

    @Override
    public void logStackTrace(String tag, Exception e) {
        if (e != null) {
            e.printStackTrace();
        }
    }

    @Override
    public float onScale(float scale) {
        return fontSize;
    }

    @Override
    public void onSingleTapUp(MotionEvent e) {
        showSoftInput();
    }

    @Override
    public boolean shouldBackButtonBeMappedToEscape() {
        return false;
    }

    @Override
    public boolean shouldEnforceCharBasedInput() {
        return true;
    }

    @Override
    public boolean shouldUseCtrlSpaceWorkaround() {
        return false;
    }

    @Override
    public boolean isTerminalViewSelected() {
        return true;
    }

    @Override
    public void copyModeChanged(boolean copyMode) {}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e, TerminalSession session) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && !session.isRunning()) {
            activity.finish();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent e) {
        return false;
    }

    @Override
    public boolean onLongPress(MotionEvent event) {
        return false;
    }

    // Keys
    @Override
    public boolean readControlKey() {
        Boolean state = activity.binding.extraKeys.readSpecialButton(SpecialButton.CTRL, true);
        return state != null && state;
    }

    @Override
    public boolean readAltKey() {
        Boolean state = activity.binding.extraKeys.readSpecialButton(SpecialButton.ALT, true);
        return state != null && state;
    }

    @Override
    public boolean readShiftKey() {
        Boolean state = activity.binding.extraKeys.readSpecialButton(SpecialButton.SHIFT, true);
        return state != null && state;
    }

    @Override
    public boolean readFnKey() {
        Boolean state = activity.binding.extraKeys.readSpecialButton(SpecialButton.FN, true);
        return state != null && state;
    }

    @Override
    public boolean onCodePoint(int codePoint, boolean ctrlDown, TerminalSession session) {
        return false;
    }

    @Override
    public void onEmulatorSet() {
        setTerminalCursorBlinkingState(true);
    }

    private void setTerminalCursorBlinkingState(boolean start) {
        if (terminal.mEmulator != null) {
            terminal.setTerminalCursorBlinkerState(start, true);
        }
    }

    private void showSoftInput() {
        terminal.requestFocus();
        KeyboardUtils.showSoftInput(terminal);
    }
}


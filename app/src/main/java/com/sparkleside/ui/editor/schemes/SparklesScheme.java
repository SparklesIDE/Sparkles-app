package com.sparkleside.ui.editor.schemes;

import com.google.android.material.color.MaterialColors;

import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;

public class SparklesScheme {
  private EditorColorScheme scheme;
  private CodeEditor editor;

  public SparklesScheme(CodeEditor editor) {
    this.editor = editor;
    scheme = editor.getColorScheme();
    var surface = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorSurface);
    var onSurface = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorOnSurface);
    var primary = MaterialColors.getColor(editor, com.google.android.material.R.attr.colorPrimary);
    scheme.setColor(EditorColorScheme.WHOLE_BACKGROUND, surface);
    scheme.setColor(EditorColorScheme.CURRENT_LINE, surface);
    scheme.setColor(EditorColorScheme.LINE_NUMBER_PANEL, surface);
    scheme.setColor(EditorColorScheme.LINE_NUMBER_BACKGROUND, surface);
    scheme.setColor(EditorColorScheme.KEYWORD, primary);
    scheme.setColor(EditorColorScheme.FUNCTION_NAME, primary);
    scheme.setColor(EditorColorScheme.TEXT_NORMAL, onSurface);
    scheme.setColor(EditorColorScheme.OPERATOR, 0xFFDDE5DB);
  }
  
  public void apply() {
    editor.setColorScheme(getScheme());
  }

  public EditorColorScheme getScheme() {
    return scheme;
  }
}
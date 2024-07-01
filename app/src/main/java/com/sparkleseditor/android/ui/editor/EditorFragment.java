package com.sparkleseditor.android.ui.editor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sparkleseditor.android.databinding.FragmentEditorBinding;

public class EditorFragment extends Fragment {

    private FragmentEditorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditorViewModel EditorViewModel =
                new ViewModelProvider(this).get(EditorViewModel.class);

        binding = FragmentEditorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textEditor;
        EditorViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root ;
   
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
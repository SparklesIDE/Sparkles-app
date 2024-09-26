package org.sparkles.editor.ui.editor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditorViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Editor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

package com.zybooks.myapplication.ui.lifts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LiftsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LiftsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.zybooks.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mWelcome;
    private final MutableLiveData<String> mProgress;

    public HomeViewModel() {
        mWelcome = new MutableLiveData<>();
        mWelcome.setValue("Welcome back!");

        mProgress = new MutableLiveData<>();
        mProgress.setValue("Let's review your recent progress:");
    }

    public LiveData<String> welcomeGetText() {
        return mWelcome;
    }

    public LiveData<String> progressGetText() {
        return mProgress;
    }
}
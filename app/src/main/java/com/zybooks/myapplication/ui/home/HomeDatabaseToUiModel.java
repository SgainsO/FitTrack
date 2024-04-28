package com.zybooks.myapplication.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import com.zybooks.myapplication.ui.lifts.*;
import com.zybooks.myapplication.ui.weight.*;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HomeDatabaseToUiModel extends AndroidViewModel
{
    private HomeRepository homeRepository;
    private LiveData<List<LiftWidget>> allLifts;
    private LiveData<List<WeightWidget>> allWeights;

    public HomeDatabaseToUiModel (Application application)
    {
        super(application);
        homeRepository = new HomeRepository(application);
        allLifts = homeRepository.getAllLiftWidgets();
        allWeights = homeRepository.getAllWeightWidgets();
    }

    public LiveData<List<LiftWidget>> getAllLifts() { return allLifts; }

    public LiveData<List<WeightWidget>> getAllWeights() { return allWeights; }

}


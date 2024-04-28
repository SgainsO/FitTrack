package com.zybooks.myapplication.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.zybooks.myapplication.ui.lifts.LiftRepository;
import com.zybooks.myapplication.ui.lifts.LiftWidget;
import com.zybooks.myapplication.ui.weight.WeightRepository;
import com.zybooks.myapplication.ui.weight.WeightWidget;

import java.util.List;

public class HomeRepository {
    private LiftRepository liftRepository;
    private WeightRepository weightRepository;
    private LiveData<List<LiftWidget>> liftWidgets;
    private LiveData<List<WeightWidget>> weightWidgets;

    public HomeRepository(Application application) {
        liftRepository = new LiftRepository(application);
        weightRepository = new WeightRepository(application);
        liftWidgets = liftRepository.getAllWidgets();
        weightWidgets = weightRepository.getAllWidgets();
    }

    public LiveData<List<LiftWidget>> getAllLiftWidgets() {
        return liftWidgets;
    }

    public LiveData<List<WeightWidget>> getAllWeightWidgets() {
        return weightWidgets;
    }
}


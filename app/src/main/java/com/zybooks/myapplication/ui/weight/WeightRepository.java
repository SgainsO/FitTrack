package com.zybooks.myapplication.ui.weight;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.zybooks.myapplication.ui.lifts.LiftWidget;

import java.util.List;

public class WeightRepository {
    private WeightDAO sqlHandler;
    private LiveData<List<WeightWidget>> widgets;

    private LiveData<List<WeightWidget>> latestWeight;

    public WeightRepository(Application application) {
        WeightDatabase db = WeightDatabase.getDatabase(application);
        sqlHandler = db.weightdao();
        widgets = sqlHandler.getWeightData();
        latestWeight = sqlHandler.getLatestData();
    }

    public LiveData<List<WeightWidget>> getAllWidgets() {
        return widgets;
    }

    public LiveData<List<WeightWidget>> getLatestData() {
        return latestWeight;
    }

    void insert(WeightWidget lw) {
        WeightDatabase.databaseWriteExecutor.execute(() -> {
            sqlHandler.updateWeightTable(lw);
        });
    }

    void delete(WeightWidget lw) {
        WeightDatabase.databaseWriteExecutor.execute(() -> {
            sqlHandler.DeleteWeight(lw);
        });
    }

}
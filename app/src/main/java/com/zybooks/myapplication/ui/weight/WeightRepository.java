package com.zybooks.myapplication.ui.weight;

import android.app.Application;

import androidx.lifecycle.LiveData;


import java.util.List;

public class WeightRepository {
    private WeightDAO sqlHandler;
    private LiveData<List<WeightWidget>> widgets;

    WeightRepository(Application application) {
        WeightDatabase db = WeightDatabase.getDatabase(application);
        sqlHandler = db.weightdao();
        widgets = sqlHandler.getWeightData();
    }

    LiveData<List<WeightWidget>> getAllWidgets() {
        return widgets;
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
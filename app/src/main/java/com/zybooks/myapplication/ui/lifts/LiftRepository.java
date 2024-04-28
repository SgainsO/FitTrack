package com.zybooks.myapplication.ui.lifts;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LiftRepository {
    private LiftsDAO sqlHandler;
    private LiveData<List<LiftWidget>> widgets;

    public LiftRepository(Application application) {
        LiftDatabase db = LiftDatabase.getDatabase(application);
        sqlHandler = db.liftdao();
        widgets = sqlHandler.getLiftData();
    }

    public LiveData<List<LiftWidget>> getAllWidgets() {
        return widgets;
    }

    void insert(LiftWidget lw) {
        LiftDatabase.databaseWriteExecutor.execute(() -> {
            sqlHandler.updateLiftTable(lw);
        });
    }

    void delete(LiftWidget lw) {
        LiftDatabase.databaseWriteExecutor.execute(() -> {
            sqlHandler.DeleteLift(lw);
        });
    }

}
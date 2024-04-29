package com.zybooks.myapplication.ui.lifts;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DatabaseToUiModel extends AndroidViewModel
{
    private LiftRepository lr;
    private final LiveData<List<LiftWidget>> allWs;

    private final LiveData<List<LiftWidget>> latestLift;

    public DatabaseToUiModel (Application application)
    {
        super(application);
        lr = new LiftRepository(application);
        allWs = lr.getAllWidgets();
        latestLift = lr.getLatestData();
    }

    public LiveData<List<LiftWidget>> getAll() {return allWs;}

    public LiveData<List<LiftWidget>> getLatestLift() {return latestLift;}

    public void insert(LiftWidget lw)
    {
        lr.insert(lw);
    }

    public void delete(LiftWidget lw)
    {
        lr.delete(lw);
    }

    public void delete(String name) {lr.deleteByName(name);}
}
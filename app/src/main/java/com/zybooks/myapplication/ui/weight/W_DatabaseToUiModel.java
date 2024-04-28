package com.zybooks.myapplication.ui.weight;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class W_DatabaseToUiModel extends AndroidViewModel
{
    private WeightRepository wr;
    private final LiveData<List<WeightWidget>> allWes;

    public W_DatabaseToUiModel(Application application)
    {
        super(application);
        wr = new WeightRepository(application);
        allWes = wr.getAllWidgets();
    }

    public LiveData<List<WeightWidget>> getAll() {return allWes;}

    public void insert(WeightWidget lw)
    {
        wr.insert(lw);
    }

    public void delete(WeightWidget lw)
    {
        wr.delete(lw);
    }
}

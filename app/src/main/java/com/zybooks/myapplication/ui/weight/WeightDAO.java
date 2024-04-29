package com.zybooks.myapplication.ui.weight;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zybooks.myapplication.ui.lifts.LiftWidget;

import java.util.List;

@Dao
public interface WeightDAO
{
    @Query("SELECT * FROM weightTable ORDER BY date DESC")
    LiveData<List<WeightWidget>> getWeightData();

    @Query("SELECT * FROM weightTable ORDER BY date DESC LIMIT 1")
    LiveData<List<WeightWidget>> getLatestData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateWeightTable(WeightWidget weightWidget);

    @Delete
    void DeleteWeight(WeightWidget weightWidget);

}

package com.zybooks.myapplication.ui.lifts;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
@Dao
public interface LiftsDAO
{
    @Query("SELECT * FROM lifttable ORDER BY name ASC")
    LiveData<List<LiftWidget>> getLiftData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateLiftTable(LiftWidget liftWidget);

    @Delete
    void DeleteLift(LiftWidget liftWidget);

}

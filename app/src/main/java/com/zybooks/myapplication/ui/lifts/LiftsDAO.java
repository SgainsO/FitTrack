package com.zybooks.myapplication.ui.lifts;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
@Dao
public interface LiftsDAO
{
    @Query("SELECT * FROM lifttable ORDER BY name ASC")
    LiveData<List<LiftWidget>> getLiftData();

    @Query("SELECT * FROM lifttable ORDER BY name DESC LIMIT 1")
    LiveData<List<LiftWidget>> getLatestData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateLiftTable(LiftWidget liftWidget);

    @Delete()
    void DeleteLift(LiftWidget liftWidget);

    @Query("DELETE FROM lifttable WHERE name = :liftId")
    void deleteLiftByName(String liftId);


}

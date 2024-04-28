package com.zybooks.myapplication.ui.lifts;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "liftTable")
public class LiftWidget {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String name;
    @NonNull
    public String reps;
    @NonNull
    public String weight;
    LiftWidget()
    {

    }

    public LiftWidget(String name, String reps, String weight)
    {
        this.name = name;
        this.reps = reps;
        this.weight = weight;
    }
}
package com.zybooks.myapplication.ui.weight;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "weightTable")
public class WeightWidget {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String weight;

    @NonNull
    public String date;

    WeightWidget(){

    }

    WeightWidget(String date, String weight){
        this.date = date;
        this.weight = weight;
    }

}

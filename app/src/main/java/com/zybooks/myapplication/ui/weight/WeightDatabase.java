package com.zybooks.myapplication.ui.weight;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WeightWidget.class}, version = 1, exportSchema = false)

public abstract class WeightDatabase extends RoomDatabase{

    public abstract WeightDAO weightdao();

    private static volatile WeightDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WeightDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeightDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    WeightDatabase.class, "weight_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WeightDAO dao = INSTANCE.weightdao();

                //WeightWidget wt = new WeightWidget("01/15/23", "15 pounds");
                //dao.updateWeightTable(wt);
            });
        }
    };
}

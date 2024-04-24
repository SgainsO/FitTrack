package com.zybooks.myapplication.ui.lifts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LiftWidget.class}, version = 1, exportSchema = false)

public abstract class LiftDatabase extends RoomDatabase
{

    public abstract LiftsDAO liftdao();

    private static volatile LiftDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static LiftDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LiftDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LiftDatabase.class, "lift_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                LiftsDAO dao = INSTANCE.liftdao();

                LiftWidget lw = new LiftWidget("Workout Card", "5 pounds", "10 pounds");
                dao.updateLiftTable(lw);
            });
        }
    };

}



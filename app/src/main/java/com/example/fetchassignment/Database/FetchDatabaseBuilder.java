package com.example.fetchassignment.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.fetchassignment.DAO.ItemDAO;
import com.example.fetchassignment.Entity.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)

public abstract class FetchDatabaseBuilder extends RoomDatabase{
    public abstract ItemDAO itemDAO();

    private static volatile FetchDatabaseBuilder INSTANCE;

    static FetchDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FetchDatabaseBuilder.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FetchDatabaseBuilder.class, "MyFetchDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();

            }
        }

        return INSTANCE;
    }
}

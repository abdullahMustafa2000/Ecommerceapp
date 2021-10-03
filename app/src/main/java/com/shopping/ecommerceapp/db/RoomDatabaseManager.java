package com.shopping.ecommerceapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shopping.ecommerceapp.modules.UserModule;

@Database(entities = {UserModule.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseManager extends RoomDatabase {

    public abstract UserDao getUserDao();
    private static RoomDatabaseManager INSTANCE;

    public static RoomDatabaseManager getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(
                    context,
                    RoomDatabaseManager.class,
                    "userData.db")
                    .fallbackToDestructiveMigration()
                    .build();
        return INSTANCE;
    }

}

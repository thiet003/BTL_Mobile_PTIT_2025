package com.exercise.app30day.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase instance;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (UserDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}

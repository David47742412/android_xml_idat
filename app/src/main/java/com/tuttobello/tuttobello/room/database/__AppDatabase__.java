package com.tuttobello.tuttobello.room.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.tuttobello.tuttobello.room.dao.UserDao;
import com.tuttobello.tuttobello.room.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class __AppDatabase__ extends RoomDatabase {
    public abstract UserDao userDao();
}

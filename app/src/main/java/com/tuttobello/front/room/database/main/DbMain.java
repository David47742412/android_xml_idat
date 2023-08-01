package com.tuttobello.front.room.database.main;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.entites.main.UserEntity;

@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class DbMain extends RoomDatabase {
    @NonNull
    public abstract IUserDao userDao();

}

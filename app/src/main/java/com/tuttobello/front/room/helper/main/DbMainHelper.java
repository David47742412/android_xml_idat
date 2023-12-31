package com.tuttobello.front.room.helper.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.database.main.DbMain;

public class DbMainHelper {
    private static final String DATABASE_NAME = "tuttobello_main";
    private static DbMain _tuttobello;
    private static IUserDao _userDao;

    private DbMainHelper() {

    }

    public static void init(@NonNull Context context) {
        _tuttobello = Room.databaseBuilder(context, DbMain.class, DATABASE_NAME).build();
        _userDao = _tuttobello.userDao();
    }

    @NonNull
    public static DbMain getDbMain() {
        return _tuttobello;
    }

    @NonNull
    public static IUserDao getUserDao() {
        return _userDao;
    }

}

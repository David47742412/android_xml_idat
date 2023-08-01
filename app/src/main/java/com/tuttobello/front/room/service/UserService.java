package com.tuttobello.front.room.service;

import androidx.annotation.NonNull;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.entites.main.UserEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;

public class UserService {
    private final IUserDao _userDao;

    public UserService(@NonNull IUserDao userDao) {
        this._userDao = userDao;
    }

    @NonNull
    public Single<List<UserEntity>> getUser() {
        return Single.create(emitter -> emitter.onSuccess(this._userDao.findAll()));
    }
    
    public void insertUser(@NonNull UserEntity user) {
        this._userDao.insert(user);
    }

}

package com.tuttobello.front.room.service;

import androidx.annotation.NonNull;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.entites.main.UserEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    @NotNull
    public Single<List<UserEntity>> insertUser(@NonNull UserEntity user) {
        return Single.create(emitter -> {
            try {
                this._userDao.insert(user);
                emitter.onSuccess(this._userDao.findAll());
            } catch (Exception ex) {
                emitter.onError(ex);
            }
        });
    }

}

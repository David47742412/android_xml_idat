package com.tuttobello.front.room.service;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.entites.main.UserEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class UserService {
    private final IUserDao _userDao;

    public UserService(IUserDao userDao) {
        this._userDao = userDao;
    }

    public Single<List<UserEntity>> getUser() {
        return Single.create(emitter -> {
            emitter.onSuccess(this._userDao.findAll());
        });
    }

}

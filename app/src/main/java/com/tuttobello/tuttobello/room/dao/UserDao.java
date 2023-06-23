package com.tuttobello.tuttobello.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tuttobello.tuttobello.room.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE email = :email")
    User findOne(String email);

    @Insert
    void insert(User... users);

    @Delete
    void delete(User user);

}

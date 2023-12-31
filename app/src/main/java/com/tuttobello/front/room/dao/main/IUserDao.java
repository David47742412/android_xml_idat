package com.tuttobello.front.room.dao.main;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tuttobello.front.room.entites.main.UserEntity;

import java.util.List;

@Dao
public interface IUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull UserEntity user);

    @NonNull
    @Query("SELECT * FROM user")
    List<UserEntity> findAll();

}

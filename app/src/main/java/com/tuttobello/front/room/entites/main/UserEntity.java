package com.tuttobello.front.room.entites.main;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class UserEntity {

    public UserEntity() {
    }

    public UserEntity(
            String userId,
            String name,
            String lastName,
            String username,
            String email,
            String token
    ) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    //id for api
    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "token")
    public String token;

    @ColumnInfo(name = "deleted")
    public boolean deleted;

}

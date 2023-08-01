package com.tuttobello.front.room.entites.main;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tuttobello.front.model.auth.RAuth;

@Entity(tableName = "User")
public class UserEntity {

    public UserEntity() {
    }

    public UserEntity(
            RAuth rAuth
    ) {
        this.userId = rAuth.usrId;
        this.name = rAuth.name;
        this.lastName = rAuth.lastName;
        this.username = rAuth.username;
        this.email = rAuth.email;
        this.token = rAuth.token;
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

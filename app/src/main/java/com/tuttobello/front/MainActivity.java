package com.tuttobello.front;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.entites.main.UserEntity;
import com.tuttobello.front.room.helper.main.DbMainHelper;
import com.tuttobello.front.room.service.UserService;
import com.tuttobello.front.ui.HomeActivity;
import com.tuttobello.front.ui.LoginActivity;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbMainHelper.init(this);
        IUserDao userDao = DbMainHelper.getDbMain().userDao();
        UserService userService = new UserService(userDao);
        userService.getUser()
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<UserEntity>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<UserEntity> userEntities) {
                        Intent onLogoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                        try {
                            if (userEntities.size() == 0) {
                                startActivity(onLogoutIntent);
                                finish();
                                return;
                            }
                            Intent onHomeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(onHomeIntent);
                            finish();
                        } catch (Exception ex) {
                            startActivity(onLogoutIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        finish();
                    }
                });
        setContentView(R.layout.activity_main);
    }
}
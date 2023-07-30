package com.tuttobello.front.network.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.database.main.DbMain;
import com.tuttobello.front.room.entites.main.UserEntity;
import com.tuttobello.front.room.helper.main.DbMainHelper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private String _token = "";

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originReq = chain.request();
        Observable<List<UserEntity>> ltsUser$ = new Observable<List<UserEntity>>() {
            @Override
            protected void subscribeActual(@io.reactivex.rxjava3.annotations.NonNull Observer<? super List<UserEntity>> observer) {
                DbMain dbMain = DbMainHelper.getDbMain();
                IUserDao userDao = dbMain.userDao();
                observer.onNext(userDao.findAll());
                observer.onComplete();
            }
        };

        ltsUser$.subscribeOn(Schedulers.io())
                .blockingSubscribe(new Observer<List<UserEntity>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<UserEntity> userEntities) {
                        if (userEntities.size() != 0) {
                            _token = userEntities.get(0).token;
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("completed--22", "algo");
                    }
                });

        if (!Objects.equals(_token, "")) {
            Request modReq = originReq.newBuilder()
                    .header("Authorization", "Bearer " + _token)
                    .build();
            return chain.proceed(modReq);
        }

        return chain.proceed(originReq);
    }


}

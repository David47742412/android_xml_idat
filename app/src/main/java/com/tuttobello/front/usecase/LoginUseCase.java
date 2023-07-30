package com.tuttobello.front.usecase;

import android.util.Log;

import com.tuttobello.front.model.auth.RAuth;
import com.tuttobello.front.model.auth.SAuth;
import com.tuttobello.front.network.RetrofitClient;
import com.tuttobello.front.network.api.IAuth;
import com.tuttobello.front.network.response.ResponseApi;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import retrofit2.Call;
import retrofit2.Response;

public class LoginUseCase {
    private final IAuth iAuth;


    public LoginUseCase() {
        this.iAuth = RetrofitClient.getClient().create(IAuth.class);
    }

    public Single<ResponseApi<RAuth>> login(String uri, SAuth sAuth) {
        return new Single<ResponseApi<RAuth>>() {
            @Override
            protected void subscribeActual(@NonNull SingleObserver<? super ResponseApi<RAuth>> observer) {
                Call<ResponseApi<RAuth>> call = iAuth.login(uri, sAuth);
                try {
                    Response<ResponseApi<RAuth>> res = call.execute();
                    if (!res.isSuccessful()) {
                        ResponseApi<RAuth> fail = new ResponseApi<>();
                        fail.message = res.message();
                        fail.statusCode = res.code();
                        observer.onSuccess(fail);
                        return;
                    }
                    observer.onSuccess(res.body());
                } catch (Exception ex) {
                    Log.e("error-login", ex.getMessage());
                }
            }
        };
    }

}

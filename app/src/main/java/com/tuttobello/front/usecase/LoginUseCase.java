package com.tuttobello.front.usecase;

import static com.tuttobello.front.network.RetrofitClient.getClient;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tuttobello.front.model.auth.RAuth;
import com.tuttobello.front.model.auth.SAuth;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.network.api.IApi;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;


public class LoginUseCase {
    private final IApi _iAuth;
    private final Gson _gson;

    public LoginUseCase() {
        this._iAuth = getClient().
                create(IApi.class);
        _gson = new Gson();
    }

    @NonNull
    public Single<ResponseApi<RAuth>> login(@NonNull String uri, @NonNull SAuth sAuth) {
        return Single.create(emitter -> {
            try {
                Call<ResponseApi<Object>> call = _iAuth.post(uri, sAuth);
                Response<ResponseApi<Object>> response = call.execute();

                if (response.isSuccessful()) {
                    Type responseType = new TypeToken<ResponseApi<RAuth>>() {
                    }.getType();
                    String responseJson = _gson.toJson(response.body());
                    ResponseApi<RAuth> res = _gson.fromJson(responseJson, responseType);
                    emitter.onSuccess(res);
                    return;
                }
                ResponseApi<RAuth> fail = new ResponseApi<>();
                fail.message = response.message();
                fail.statusCode = response.code();
                emitter.onSuccess(fail);
            } catch (Exception ex) {
                Log.e("error-login", ex.getMessage());
                emitter.onError(ex);
            }
        });
    }
}

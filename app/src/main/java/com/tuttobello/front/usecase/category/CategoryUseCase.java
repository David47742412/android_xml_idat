package com.tuttobello.front.usecase.category;

import static com.tuttobello.front.network.RetrofitClient.getClient;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tuttobello.front.model.category.RCategory;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.network.api.IApi;

import java.lang.reflect.Type;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;

public class CategoryUseCase {

    private final IApi _iCategory;
    private final Gson _gson;

    public CategoryUseCase() {
        this._iCategory = getClient().
                create(IApi.class);
        _gson = new Gson();
    }

    @NonNull
    public Single<ResponseApi<RCategory>> getCategories(@NonNull String uri) {
        return Single.create(emitter -> {
            try {
                Call<ResponseApi<Object>> call = _iCategory.get(uri);
                Response<ResponseApi<Object>> response = call.execute();
                if (!response.isSuccessful()) {
                    ResponseApi<RCategory> fail = new ResponseApi<>();
                    fail.message = response.message();
                    fail.statusCode = response.code();
                    emitter.onSuccess(fail);
                    return;
                }
                Type responseType = new TypeToken<ResponseApi<RCategory>>() {
                }.getType();
                String responseJson = _gson.toJson(response.body());
                ResponseApi<RCategory> res = _gson.fromJson(responseJson, responseType);
                //en caso existoso
                emitter.onSuccess(res);
            } catch (Exception ex) {
                Log.e("error-book", Objects.requireNonNull(ex.getMessage()));
                emitter.onError(ex);
            }
        });
    }
}

package com.tuttobello.front.usecase.book;

import static com.tuttobello.front.network.RetrofitClient.getClient;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tuttobello.front.model.book.RBook;
import com.tuttobello.front.model.book.SBook;
import com.tuttobello.front.model.response.ResponseApi;
import com.tuttobello.front.network.api.IApi;
import com.tuttobello.front.room.dao.main.IUserDao;
import com.tuttobello.front.room.entites.main.UserEntity;
import com.tuttobello.front.room.helper.main.DbMainHelper;
import com.tuttobello.front.room.service.UserService;

import java.lang.reflect.Type;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;

public class BookUseCase {

    private final IApi _iBook;
    private final Gson _gson;

    public BookUseCase() {
        this._iBook = getClient().
                create(IApi.class);
        _gson = new Gson();
    }

    @NonNull
    public Single<ResponseApi<RBook>> createBook(@NonNull String uri, @NonNull SBook sBook) {
        return Single.create(emitter -> {
            try {
                Call<ResponseApi<Object>> call = _iBook.post(uri, sBook);
                Response<ResponseApi<Object>> response = call.execute();
                if (!response.isSuccessful()) {
                    ResponseApi<RBook> fail = new ResponseApi<>();
                    fail.message = response.message();
                    fail.statusCode = response.code();
                    emitter.onSuccess(fail);
                    return;
                }
                Type responseType = new TypeToken<ResponseApi<RBook>>() {
                }.getType();
                String responseJson = _gson.toJson(response.body());
                ResponseApi<RBook> res = _gson.fromJson(responseJson, responseType);
                //en caso existoso
                emitter.onSuccess(res);
            } catch (Exception ex) {
                Log.e("error-book", Objects.requireNonNull(ex.getMessage()));
                emitter.onError(ex);
            }
        });
    }

    @NonNull
    public Single<ResponseApi<RBook>> updateBook(@NonNull String uri, @NonNull SBook sBook) {
        return Single.create(emitter -> {
            try {
                Call<ResponseApi<Object>> call = _iBook.put(uri, sBook);
                Response<ResponseApi<Object>> response = call.execute();
                if (!response.isSuccessful()) {
                    ResponseApi<RBook> fail = new ResponseApi<>();
                    fail.message = response.message();
                    fail.statusCode = response.code();
                    emitter.onSuccess(fail);
                    return;
                }
                Type responseType = new TypeToken<ResponseApi<RBook>>() {
                }.getType();
                String responseJson = _gson.toJson(response.body());
                ResponseApi<RBook> res = _gson.fromJson(responseJson, responseType);
                //en caso existoso
                emitter.onSuccess(res);
            } catch (Exception ex) {
                Log.e("error-book", Objects.requireNonNull(ex.getMessage()));
                emitter.onError(ex);
            }
        });
    }

    @NonNull
    public Single<ResponseApi<RBook>> deleteBook(@NonNull String uri) {
        return Single.create(emitter -> {
            try {
                Call<ResponseApi<Object>> call = _iBook.delete(uri);
                Response<ResponseApi<Object>> response = call.execute();
                if (!response.isSuccessful()) {
                    ResponseApi<RBook> fail = new ResponseApi<>();
                    fail.message = response.message();
                    fail.statusCode = response.code();
                    emitter.onSuccess(fail);
                    return;
                }
                Type responseType = new TypeToken<ResponseApi<RBook>>() {
                }.getType();
                String responseJson = _gson.toJson(response.body());
                ResponseApi<RBook> res = _gson.fromJson(responseJson, responseType);
                //en caso existoso
                emitter.onSuccess(res);
            } catch (Exception ex) {
                Log.e("error-book", Objects.requireNonNull(ex.getMessage()));
                emitter.onError(ex);
            }
        });
    }
}

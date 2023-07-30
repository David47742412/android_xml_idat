package com.tuttobello.front.network.api;

import com.tuttobello.front.model.auth.RAuth;
import com.tuttobello.front.model.auth.SAuth;
import com.tuttobello.front.network.response.ResponseApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface IAuth {
    //@GET()
    //Call<> getProfile(@Url() String url);

    @POST()
    Call<ResponseApi<RAuth>> login(@Url String uri, @Body() SAuth sAuth);

}

package com.tuttobello.front.network.api;

import com.tuttobello.front.model.response.ResponseApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface IApi {
    @GET()
    Call<ResponseApi<Object>> get(@Url() String url);

    @POST()
    Call<ResponseApi<Object>> post(@Url String uri, @Body() Object body);

    @PUT
    Call<ResponseApi<Object>> put(@Url String uri, @Body() Object body);

    @DELETE
    Call<ResponseApi<Object>> delete(@Url String uri, @Body() Object body);

}

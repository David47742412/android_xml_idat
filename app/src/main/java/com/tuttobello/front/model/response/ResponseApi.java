package com.tuttobello.front.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseApi<T> {
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("message")
    public String message;
    @SerializedName("count")
    public int count;
    @SerializedName("body")
    public List<T> body;
}

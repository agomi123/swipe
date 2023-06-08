package com.example.swipe;

import com.example.swipe.Models.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("api/public/get")
    Call<String> STRING_CALL();

    @POST("api/public/add")
    Call<Product> createPost(@Body Product dataModal);
}


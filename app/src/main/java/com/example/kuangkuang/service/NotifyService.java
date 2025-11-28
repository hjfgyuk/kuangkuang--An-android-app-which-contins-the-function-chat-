package com.example.kuangkuang.service;

import com.example.kuangkuang.entity.Notify;
import com.example.kuangkuang.entity.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotifyService {
    @GET("/notify/{userId}")
    Call<Result<List<Notify>>> getNotifies(@Path("userId") int userid);
    @POST("/notify/add")
    Call<Result> addNotify(@Body Notify notify);
}

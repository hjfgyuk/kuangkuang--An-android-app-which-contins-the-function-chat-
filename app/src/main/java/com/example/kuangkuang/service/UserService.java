package com.example.kuangkuang.service;

import com.example.kuangkuang.data.AndroidResult;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface UserService {
    @POST("/user/login")
    Call<Result<User>> getUserByName(@Body User user);

    @POST("/user/logout")
    Call<Result> logout();
    @POST("/user/update")
    Call<Result> update(@Body User user);
}

package com.example.kuangkuang.service;

import com.example.kuangkuang.data.Result;
import com.example.kuangkuang.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface UserService {
    @POST("/user/login")
    Call<User> getUserByName(@Body User user);

}

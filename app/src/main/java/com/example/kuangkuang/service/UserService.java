package com.example.kuangkuang.service;

import com.example.kuangkuang.R;
import com.example.kuangkuang.data.AndroidResult;
import com.example.kuangkuang.entity.Friend;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("/user/login")
    Call<Result<User>> getUserByName(@Body User user);
    @POST("/user/logout")
    Call<Result> logout();
    @POST("/user/update")
    Call<Result> update(@Body User user);
    @POST("/user/get")
    Call<Result<User>> getUser(@Body User user);
    @POST("/friend/add")
    Call<Result> addFriend(@Body Friend friend);
    @GET("/friend/{userId}")
    Call<Result<List<Friend>>> getByUserId(@Path("userId") int id);
    @DELETE("/friend/delete/{id}")
    Call<Result> deleteFriend(@Path("id") String id);
}

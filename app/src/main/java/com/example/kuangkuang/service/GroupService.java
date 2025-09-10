package com.example.kuangkuang.service;

import com.example.kuangkuang.entity.Group;
import com.example.kuangkuang.entity.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupService {
    @POST("/group/{userId}")
    Call<Result<List<Group>>> getByGroupId(@Path("userId") int usrId);
    @DELETE("/group/delete/{id}")
    Call<Result> delete(@Query("id") int groupId);
    @POST("/group/add")
    Call<Result> add(@Body Group group);
}

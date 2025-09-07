package com.example.kuangkuang.service;

import com.example.kuangkuang.entity.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupService {
    @POST("/group/{userId}")
    Call<List<Group>> getByGroupId(@Path("userId") int usrId);
    @DELETE("/group/delete/{id}")
    Call<Boolean> delete(@Query("id") int groupId);
    @POST("/group/add")
    Call<Boolean> add(@Body Group group);
}

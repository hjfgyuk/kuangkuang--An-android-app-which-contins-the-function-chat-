package com.example.kuangkuang.service;

import com.example.kuangkuang.R;
import com.example.kuangkuang.entity.Group;
import com.example.kuangkuang.entity.Message;
import com.example.kuangkuang.entity.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessageService {
 @GET("/group/{groupId}")
    Call<Result<List<Message>>> getByGroupId(@Path("groupId") String groupId);

}

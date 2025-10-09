package com.example.kuangkuang.factory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofitFactory  {
    //TODO 设置自己的ip地址
    private String mybaseUrl = " ***8:8080";
    public Retrofit setRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mybaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}

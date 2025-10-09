package com.example.kuangkuang.factory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofitFactory  {
    private String mybaseUrl = "http://192.168.43.98:8080";
    public Retrofit setRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mybaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}

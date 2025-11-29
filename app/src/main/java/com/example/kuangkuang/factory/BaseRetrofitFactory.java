package com.example.kuangkuang.factory;

import com.example.kuangkuang.Interceptor.AuthInterceptor;
import com.example.kuangkuang.context.BaseContext;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofitFactory  {
    //TODO 设置自己的ip地址
    private String mybaseUrl = "http://10.0.2.2:8080";
    public Retrofit setRetrofit(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new AuthInterceptor(BaseContext.getToken()!=null?BaseContext.getToken():null));
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(mybaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}

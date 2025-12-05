package com.example.thiennguyen.view.data;

import com.example.thiennguyen.api.bangtin.RetrofitClient;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String URL_BASE ="http://192.168.1.24:8080/identity/";
    public static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
//    public static ChienDichApi getChienDichApi(){
//        return getRetrofit().create(ChienDichApi.class);
//    }
}

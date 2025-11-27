package com.example.thiennguyen.api.bangtin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // DÙNG CHO MÁY ẢO ANDROID


    // DÙNG CHO ĐIỆN THOẠI THẬT → bỏ comment dòng dưới và sửa IP máy tính của bạn
    private static final String BASE_URL = "http://192.168.1.81:5089/";

    private static Retrofit retrofit;

    public static ApiService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
package com.example.thiennguyen.api.bangtin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.1.81:5089/";

    private static Retrofit retrofit;

    public static ApiService getService() {
        if (retrofit == null) {
            // Cấu hình Gson để hiểu định dạng ngày tháng từ API
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // Sử dụng Gson đã được cấu hình
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}

package com.example.thiennguyen.api.bangtin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.114.213:5089/";

    private static Retrofit retrofit;

    public static ApiService getService() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy HH:mm") // SỬA: Đổi định dạng ngày tháng
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}

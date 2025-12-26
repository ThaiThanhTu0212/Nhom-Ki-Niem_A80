package com.example.thiennguyen.view.data;

import com.example.thiennguyen.api.bangtin.RetrofitClient;
import com.example.thiennguyen.view.data.api.ChienDichApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String URL_BASE ="http://192.168.1.82:8080/identity/";
    
    // Adapter để parse LocalDateTime từ JSON
    private static final JsonDeserializer<LocalDateTime> localDateTimeDeserializer = new JsonDeserializer<LocalDateTime>() {
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull()) {
                return null;
            }
            String dateStr = json.getAsString();
            if (dateStr == null || dateStr.isEmpty()) {
                return null;
            }
            try {
                // Parse format: "2025-12-24T22:39:38.907"
                // Loại bỏ milliseconds nếu có
                if (dateStr.contains(".")) {
                    dateStr = dateStr.substring(0, dateStr.lastIndexOf("."));
                }
                return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (Exception e) {
                throw new JsonParseException("Error parsing LocalDateTime: " + dateStr, e);
            }
        }
    };
    
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer)
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

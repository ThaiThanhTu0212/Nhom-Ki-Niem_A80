package com.example.thiennguyen.view.data.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreference {
    private Context context;

    public MySharePreference(Context context) {
        this.context = context;
    }
    public void putBooleanValue(String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyShare",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public void getBooleanValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyShare",Context.MODE_PRIVATE);
        sharedPreferences.getBoolean(key,false);
    }
    public void putStringToken(String key, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,token).apply();
    }
    public String getStringToken(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

}

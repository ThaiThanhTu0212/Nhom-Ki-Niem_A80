package com.example.thiennguyen.view.data.sharepreference;

import android.content.Context;

public class DataLocalManager {
    private static DataLocalManager instance;
    private MySharePreference mySharePreference;
    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharePreference = new MySharePreference(context);
    }
    public static DataLocalManager getInstance(){
        if (instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setFisrtInstall(boolean isFirst){
        DataLocalManager.getInstance().mySharePreference.putBooleanValue("FIRST_INSTALL",isFirst);
    }
    public static void getFirstInstall(){
        DataLocalManager.getInstance().mySharePreference.getBooleanValue("FIRST_INSTALL");
    }
    public static  void setToken(String token){
        DataLocalManager.getInstance().mySharePreference.putStringToken("TOKEN",token);
    }
    public static String getToken(){
        return DataLocalManager.getInstance().mySharePreference.getStringToken("TOKEN");
    }
}

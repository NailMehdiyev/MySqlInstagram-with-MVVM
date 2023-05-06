package com.nailmehdiyev.mymysqlinstagram.local;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreferen {

    public static  String getSharedPreferenceuser(Context context,String key,String defaultValue) {

       return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(key, defaultValue);

    }


    public static void addSharedPreferenceuser(Context context, String key, String value) {

        SharedPreferences.Editor sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        sharedPref.putString(key, value);

        sharedPref.apply();

    }


    public static void removeSharedPreferenceuser(Context context,String key) {
        SharedPreferences.Editor sharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        sharedPref.remove(key);
        sharedPref.apply();

    }



}

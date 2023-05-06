package com.nailmehdiyev.mymysqlinstagram.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    private static Gson gson=new GsonBuilder().setLenient().create();

    public static Retrofit  retrofit(String Baseurl) {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor("nail", "19921995"))
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(Baseurl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return    retrofit;


    }



}

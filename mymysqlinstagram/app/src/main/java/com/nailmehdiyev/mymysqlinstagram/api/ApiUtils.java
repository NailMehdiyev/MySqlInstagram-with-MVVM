package com.nailmehdiyev.mymysqlinstagram.api;

public class ApiUtils {//192.168.1.103
    public static final String Baseurl = "http://192.168.1.103/nail/";


    public static ApiInterface getApiService() {
        return ApiClient.retrofit(Baseurl).create(ApiInterface.class);

    }
}
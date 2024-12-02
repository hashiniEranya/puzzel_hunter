package com.example.nb_minegame.repo;


public class ApiUtil {
    public static API getAPIService(String url) {
        return RetrofitClient.getClient(url).create(API.class);
    }
}

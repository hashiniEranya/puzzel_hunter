package com.example.nb_minegame.repo;


import com.example.nb_minegame.model.QuizResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface API {
    @GET("/uob/smile/api.php")
    Call<QuizResponse> fetchQuize();


}

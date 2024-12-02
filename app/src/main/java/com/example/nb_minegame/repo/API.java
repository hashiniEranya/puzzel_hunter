package com.example.nb_minegame.repo;


import com.example.nb_minegame.model.QuizResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("/uob/banana/api.php")
    Call<QuizResponse> fetchQuize();


}

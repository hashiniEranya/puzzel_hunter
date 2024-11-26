package com.example.nb_minegame.repo;

import android.app.Activity;

import com.example.nb_minegame.R;
import com.example.nb_minegame.listener.OnNetworkResponseListener;
import com.example.nb_minegame.model.QuizResponse;
import com.example.nb_minegame.util.LogUtil;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author chalana
 * @created 2023/02/09 | 9:38 AM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */


public class Repository {

    private static Retrofit retrofit;
    private static Repository apiService;
    private API api;
    private HashMap<String, String> headers;
    private Activity mActivity;
    private static final String TAG = "ApiService ::: ";

    public Repository(Activity mActivity) {
        api = ApiUtil.getAPIService("https://marcconrad.com");
    }

    //fetch question
    public void fetchQuestion(OnNetworkResponseListener listener) {
        api.fetchQuize().enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccessResponse(response.body());
                    LogUtil.debug(TAG, "RESPONSE : " + new Gson().toJson(response.body()));
                } else if (response.code() >= 500) {
                    listener.onErrorResponse(mActivity.getString(R.string.server_error_message));
                } else {
                    try {
                        listener.onErrorResponse(response.errorBody().string());
                    } catch (Exception e) {
                        listener.onErrorResponse(e.getLocalizedMessage());
                    }
                    LogUtil.debug(TAG, "RESPONSE : Failed !!! > " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                listener.onErrorResponse(t.getLocalizedMessage());
                LogUtil.debug(TAG, "RESPONSE : Failed !!! > " + t.getLocalizedMessage());
            }
        });
    }


}

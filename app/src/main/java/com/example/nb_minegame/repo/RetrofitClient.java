package com.example.nb_minegame.repo;

import com.example.nb_minegame.BuildConfig;
import com.example.nb_minegame.util.LogUtil;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author chalana
 * @created 2023/02/09 | 9:16 AM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> LogUtil.debug("<--API_CLIENT-->", message));
            logging.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);

            if (BuildConfig.DEBUG) {
                mBuilder.addInterceptor(logging);
            }


            OkHttpClient client = mBuilder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().setLenient().create()))
                    .build();
        }
        return retrofit;
    }
}

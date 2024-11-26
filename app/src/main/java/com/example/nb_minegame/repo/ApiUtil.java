package com.example.nb_minegame.repo;

/**
 * @author chalana
 * @created 2023/02/09 | 11:44 AM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */
public class ApiUtil {
    public static API getAPIService(String url) {
        return RetrofitClient.getClient(url).create(API.class);
    }
}

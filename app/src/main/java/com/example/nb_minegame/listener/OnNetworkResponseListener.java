package com.example.nb_minegame.listener;

/**
 * @author chalana
 * @created 2023/03/12 | 10:07 AM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */
public interface OnNetworkResponseListener<X, String> {

    void onSuccessResponse(X response);

    void onErrorResponse(String response);

    void onNetworkError();
}

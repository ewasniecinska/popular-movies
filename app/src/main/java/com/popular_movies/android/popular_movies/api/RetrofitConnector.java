package com.popular_movies.android.popular_movies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ewasniecinska on 13.08.2018.
 */

public class RetrofitConnector {
    private static Retrofit retrofit;
    private MoviesService service;

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static MoviesService getService(){
        MoviesService service = getRetrofit().create(MoviesService.class);
        return service;
    }
}

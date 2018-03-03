package com.popular_movies.android.popular_movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ewasniecinska on 20.02.2018.
 */

public interface MoviesService {

    // https://api.themoviedb.org/3/movie/top_rated?api_key=API_KEY

    @GET("movie/top_rated")
    Call<Movies> getTopRatedMovies(@Query("api_key") String user);

    @GET("movie/popular")
    Call<Movies> getMostPopularMovies(@Query("api_key") String user);


}

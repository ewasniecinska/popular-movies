package com.popular_movies.android.popular_movies.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ewasniecinska on 20.02.2018.
 */

public interface MoviesService {

    // https://api.themoviedb.org/3/movie/top_rated?api_key=API_KEY

    @GET("movie/top_rated")
    Call<DiscoverResult> getTopRatedMovies(@Query("api_key") String user);

    @GET("movie/popular")
    Call<DiscoverResult> getMostPopularMovies(@Query("api_key") String user);

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") String movieId, @Query("api_key") String user);

    @GET("movie/{id}/videos")
    Call<MovieTrailerResult> getMovieTrailers(@Path("id") String movieId, @Query("api_key") String user);

    @GET("movie/{id}/reviews")
    Call<MovieReviewResult> getMovieReviews(@Path("id") String movieId, @Query("api_key") String user);
}

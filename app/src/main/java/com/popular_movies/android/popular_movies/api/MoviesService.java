package com.popular_movies.android.popular_movies.api;

import com.popular_movies.android.popular_movies.models.DiscoverResult;
import com.popular_movies.android.popular_movies.models.Movie;
import com.popular_movies.android.popular_movies.models.MovieReviewResult;
import com.popular_movies.android.popular_movies.models.MovieTrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ewasniecinska on 20.02.2018.
 */

public interface MoviesService {

    @GET("movie/top_rated")
    Call<DiscoverResult> getTopRatedMovies(@Query("api_key") String user);

    @GET("movie/popular")
    Call<DiscoverResult> getMostPopularMovies(@Query("api_key") String user);

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") String movieId, @Query("api_key") String user);

    @GET("movie/{id}/videos")
    Call<MovieTrailerResult> getMovieTrailers(@Path("id") Integer movieId, @Query("api_key") String user);

    @GET("movie/{id}/reviews")
    Call<MovieReviewResult> getMovieReviews(@Path("id") Integer movieId, @Query("api_key") String user);
}

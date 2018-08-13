package com.popular_movies.android.popular_movies.models;

import java.util.List;

/**
 * Created by ewasniecinska on 21.03.2018.
 */

public class MovieTrailerResult {
    private int id;
    private List<MovieTrailer> results;

    public MovieTrailerResult(int id, List<MovieTrailer> results){
        this.id = id;
        this.results = results;
    }

    public List<MovieTrailer> getMovieTrailers(){
        return results;
    }
}

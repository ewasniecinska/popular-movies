package com.popular_movies.android.popular_movies.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ewasniecinska on 20.02.2018.
 */

public class DiscoverResult {
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    private List<Movie> results;

    public DiscoverResult(int page, int totalResults, int totalPages, List<Movie> results){

        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public List<Movie> getListOfMovies(){
        return results;
    }

}

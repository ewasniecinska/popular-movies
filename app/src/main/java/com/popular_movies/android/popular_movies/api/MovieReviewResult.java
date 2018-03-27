package com.popular_movies.android.popular_movies.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ewasniecinska on 21.03.2018.
 */

public class MovieReviewResult {

        private long id;
        private int page;
        private List<MovieReview> results;
        @SerializedName("total_results")
        private int totalResults;
        @SerializedName("total_pages")
        private int totalPages;


        public MovieReviewResult(long id, int page, List<MovieReview> results, int totalResults, int totalPages){

            this.page = page;
            this.totalResults = totalResults;
            this.totalPages = totalPages;
            this.results = results;
        }


        public int getTotalResults() {
            return totalResults;
        }

        public List<MovieReview> getMovieReviews() {
            return results;
        }
}


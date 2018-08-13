package com.popular_movies.android.popular_movies.models;

import java.util.List;

/**
 * Created by ewasniecinska on 21.03.2018.
 */

public class MovieReviewResult {

        private int id;
        private int page;
        private List<MovieReview> results;
        private int total_pages;
        private int total_results;



        public MovieReviewResult(int id, int page, List<MovieReview> results, int total_pages, int total_results){

            this.id = id;
            this.page = page;
            this.results = results;
            this.total_pages = total_pages;
            this.total_results = total_results;

        }


        public int getTotalResults() {
            return total_results;
        }

        public List<MovieReview> getMovieReviews() {
            return results;
        }
}


package com.popular_movies.android.popular_movies.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ewasniecinska on 21.03.2018.
 */

public class MovieReview {

    private String author;
    private String content;
    @SerializedName("id")
    private long reviewId;
    @SerializedName("url")
    private String reviewUrl;

    public MovieReview(String author, String content, long reviewId, String reviewUrl){
        this.author = author;
        this.content = content;
        this.reviewId = reviewId;
        this.reviewUrl = reviewUrl;
    }

    public String getReviewAuthor (){
        return author;
    }

    public String getReviewContent() {
        return content;
    }
}

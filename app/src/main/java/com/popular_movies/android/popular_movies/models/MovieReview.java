package com.popular_movies.android.popular_movies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ewasniecinska on 21.03.2018.
 */

public class MovieReview implements Parcelable{

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

    protected MovieReview(Parcel in) {
        author = in.readString();
        content = in.readString();
        reviewId = in.readLong();
        reviewUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeLong(reviewId);
        dest.writeString(reviewUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    public String getReviewAuthor (){
        return author;
    }

    public String getReviewContent() {
        return content;
    }

    public long getReviewId() {
        return reviewId;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }
}

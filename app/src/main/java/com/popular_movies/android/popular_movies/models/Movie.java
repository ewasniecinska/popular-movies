package com.popular_movies.android.popular_movies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ewasniecinska on 20.02.2018.
 */

public class Movie implements Parcelable{

    @SerializedName("vote_count")
    private int voteCount;
    private int id;
    private boolean video;
    @SerializedName("vote_average")
    private String voteAverage;
    private String title;
    private Float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_language")
    private String orginalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("genre_ids")
    private List<Integer> genreIDs;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private boolean adult;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie(int voteCount, int id, boolean video, String voteAverage, String title,
                 Float popularity, String posterPath, String orginalLanguage, String originalTitle,
                 List<Integer> genreIDs, String backdropPath, boolean adult, String overview,
                 String releaseDate){

        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.orginalLanguage = orginalLanguage;
        this.originalTitle = originalTitle;
        this.genreIDs = genreIDs;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;

    }

    protected Movie(Parcel in) {
        voteCount = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        voteAverage = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readFloat();
        }
        posterPath = in.readString();
        orginalLanguage = in.readString();
        originalTitle = in.readString();
        backdropPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeInt(id);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeString(voteAverage);
        dest.writeString(title);
        if (popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(popularity);
        }
        dest.writeString(posterPath);
        dest.writeString(orginalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(backdropPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId(){
        return id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getOrginalLanguage() {
        return orginalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenres() {
        return genreIDs;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean getIfAdult(){
        return adult;
    }

    public String getOverview(){
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}

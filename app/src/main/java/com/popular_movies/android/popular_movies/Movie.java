package com.popular_movies.android.popular_movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ewasniecinska on 20.02.2018.
 */

public class Movie {

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

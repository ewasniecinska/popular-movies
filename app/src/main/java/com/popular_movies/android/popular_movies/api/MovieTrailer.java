package com.popular_movies.android.popular_movies.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ewasniecinska on 21.03.2018.
 */

public class MovieTrailer {

    @SerializedName("id")
    private String trailerid;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;


    public MovieTrailer(String trailerid, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type){
        this.trailerid = trailerid;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getTrailerName(){
        return name;
    }

    public String getTrailerUrl(){
        String baseYouTubeUrl = "https://www.youtube.com/watch?v=";
        return baseYouTubeUrl + key;
    }
}

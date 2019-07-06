package com.ehedgehog.android.getmovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

public class MoviesSearchResult extends RealmObject implements Serializable {

    @SerializedName("Title")
    private String mTitle;
    @SerializedName("Year")
    private String mYear;
    @SerializedName("imdbID")
    private String mImdbId;
    @SerializedName("Type")
    private String mType;
    @SerializedName("Poster")
    private String mPosterUrl;

    public MoviesSearchResult() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public void setImdbId(String imdbId) {
        mImdbId = imdbId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getPosterUrl() {
        if (mPosterUrl.equals("N/A"))
            return null;
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }
}

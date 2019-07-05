package com.ehedgehog.android.getmovies.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

import java.io.Serializable;

public class Movie extends RealmObject implements Serializable {

    @SerializedName("Title")
    private String mTitle;
    @SerializedName("Year")
    private String mYear;
    @SerializedName("Released")
    private String mReleased;
    @SerializedName("Runtime")
    private String mRuntime;
    @SerializedName("Genre")
    private String mGenre;
    @SerializedName("Director")
    private String mDirector;
    @SerializedName("Writer")
    private String mWriter;
    @SerializedName("Actors")
    private String mActors;
    @SerializedName("Plot")
    private String mPlot;
    @SerializedName("Language")
    private String mLanguage;
    @SerializedName("Country")
    private String mCountry;
    @SerializedName("Awards")
    private String mAwards;
    @SerializedName("Poster")
    private String mPosterUrl;
    @SerializedName("imdbRating")
    private String mImdbRating;
    @SerializedName("imdbVotes")
    private String mImdbVotes;
    @SerializedName("imdbId")
    private String mImdbId;
    @SerializedName("Type")
    private String mType;
    @SerializedName("totalSeasons")
    private String mTotalSeasons;
    @SerializedName("Response")
    private String mResponse;

    public Movie() {
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

    public String getReleased() {
        return mReleased;
    }

    public void setReleased(String released) {
        mReleased = released;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public void setRuntime(String runtime) {
        mRuntime = runtime;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public String getWriter() {
        return mWriter;
    }

    public void setWriter(String writer) {
        mWriter = writer;
    }

    public String getActors() {
        return mActors;
    }

    public void setActors(String actors) {
        mActors = actors;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String plot) {
        mPlot = plot;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getAwards() {
        return mAwards;
    }

    public void setAwards(String awards) {
        mAwards = awards;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        mPosterUrl = posterUrl;
    }

    public String getImdbRating() {
        return mImdbRating;
    }

    public void setImdbRating(String imdbRating) {
        mImdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return mImdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        mImdbVotes = imdbVotes;
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

    public String getTotalSeasons() {
        return mTotalSeasons;
    }

    public void setTotalSeasons(String totalSeasons) {
        mTotalSeasons = totalSeasons;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }
}

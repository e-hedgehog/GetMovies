package com.ehedgehog.android.getmovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoviesResponse implements Serializable {

    @SerializedName("Response")
    private boolean mResponse;
    @SerializedName("totalResults")
    private int mTotalResults;
    @SerializedName("Search")
    private List<MoviesSearchResult> mMoviesSearchResults;

    public MoviesResponse() {
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public List<MoviesSearchResult> getMoviesSearchResults() {
        if (mMoviesSearchResults == null)
            mMoviesSearchResults = new ArrayList<>();
        return mMoviesSearchResults;
    }

    public void setMoviesSearchResults(List<MoviesSearchResult> moviesSearchResults) {
        mMoviesSearchResults = moviesSearchResults;
    }

    public boolean hasResponse() {
        return mResponse;
    }

    public void setResponse(boolean response) {
        mResponse = response;
    }
}

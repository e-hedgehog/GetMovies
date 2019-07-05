package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehedgehog.android.getmovies.model.MoviesSearchResult;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Context mContext;
    private List<MoviesSearchResult> mMovies;

    public MoviesAdapter(Context context, List<MoviesSearchResult> movies) {
        mContext = context;
        mMovies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_item, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        movieHolder.bind(mMovies.get(i));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(List<MoviesSearchResult> movies) {
        mMovies = movies;
    }

    public void addAll(List<MoviesSearchResult> movieReviews) {
        mMovies.addAll(movieReviews);
    }
}

package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehedgehog.android.getmovies.model.MoviesSearchResult;
import com.squareup.picasso.Picasso;

public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mMovieImage;
    private TextView mTitleTextView;
    private TextView mYearTextView;

    private MoviesSearchResult mMovie;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        mMovieImage = itemView.findViewById(R.id.movie_item_image);
        mTitleTextView = itemView.findViewById(R.id.movie_item_title);
        mYearTextView = itemView.findViewById(R.id.movie_item_year);
    }

    public void bind(MoviesSearchResult movie) {
        mMovie = movie;

        mTitleTextView.setText(movie.getTitle());
        mYearTextView.setText(movie.getYear());
        if (movie.getPosterUrl() != null) {
            mMovieImage.setVisibility(View.VISIBLE);
            Picasso.get().load(movie.getPosterUrl())
                    .fit().centerCrop().into(mMovieImage);
        } else
            mMovieImage.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
//        Context context = v.getContext();
//        Intent i = ReviewDetailsActivity.newIntent(context, mMovie);
//        context.startActivity(i);
    }
}

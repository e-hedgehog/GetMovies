package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MovieDetailsActivity extends SingleFragmentActivity {

    private static final String EXTRA_MOVIE = "com.ehedgehog.android.moviereviews.movie";

    public static Intent newIntent(Context context, String movieId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movieId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String movieId = getIntent().getStringExtra(EXTRA_MOVIE);
        return MovieDetailsFragment.newInstance(movieId);
    }
}

package com.ehedgehog.android.getmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ehedgehog.android.getmovies.model.Movie;
import com.ehedgehog.android.getmovies.network.ApiFactory;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.disposables.Disposable;

public class MovieDetailsFragment extends Fragment implements MoviePresenter.Listener{

    private static final String TAG = "MovieDetailsFragment";
    private static final String ARG_MOVIE = "MovieId";

    private ImageView mMovieImage;
    private TextView mTitleTextView;
    private TextView mRatingTextView;
    private TextView mReleasedTextView;
    private TextView mCountryTextView;
    private TextView mLanguageTextView;
    private TextView mAwardsTextView;
    private TextView mGenreTextView;
    private TextView mWriterTextView;
    private TextView mActorsTextView;
    private TextView mPlotTextView;

    private String mMovieId;

    private MoviePresenter mPresenter;
    private Disposable mMovieSubscription;

    public static MovieDetailsFragment newInstance(String movieId) {
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE, movieId);

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null)
            mMovieId = getArguments().getString(ARG_MOVIE);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        setupPresenter();

        mMovieImage = view.findViewById(R.id.movie_image);
        mTitleTextView = view.findViewById(R.id.movie_title);
        mRatingTextView = view.findViewById(R.id.movie_rating);
        mReleasedTextView = view.findViewById(R.id.movie_released);
        mCountryTextView = view.findViewById(R.id.movie_country);
        mLanguageTextView = view.findViewById(R.id.movie_language);
        mAwardsTextView = view.findViewById(R.id.movie_awards);
        mGenreTextView = view.findViewById(R.id.movie_genre);
        mWriterTextView = view.findViewById(R.id.movie_writer);
        mActorsTextView = view.findViewById(R.id.movie_actors);
        mPlotTextView = view.findViewById(R.id.movie_plot);

        Log.i(TAG, mMovieId == null ? "id is null" : "id is not null");
        if (mMovieId != null)
            loadMovie(mMovieId);

        return view;
    }

    @Override
    public void onMovieLoaded(Movie movie) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            String type = movie.getType();
            type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
            activity.getSupportActionBar().setTitle(type);
            activity.getSupportActionBar()
                    .setSubtitle(movie.getYear());
        }

        Log.i(TAG, movie.getPosterUrl());
        Picasso.get().load(movie.getPosterUrl()).into(mMovieImage);

        mTitleTextView.setText(movie.getTitle());
        mRatingTextView.setText(String
                .format("%s (%s votes)", movie.getImdbRating(), movie.getImdbVotes()));
        mReleasedTextView.setText(movie.getReleased());
        mCountryTextView.setText(movie.getCountry());
        mLanguageTextView.setText(movie.getLanguage());
        mAwardsTextView.setText(movie.getAwards());
        mGenreTextView.setText(movie.getGenre());
        mWriterTextView.setText(movie.getWriter());
        mActorsTextView.setText(movie.getActors());
        mPlotTextView.setText(movie.getPlot());

        mMovieSubscription.dispose();
    }

    @Override
    public void onLoadingError(String message) {
        Log.e(TAG, message);
    }

//    private String formatDate(String date) {
//        if (date.equals("Not Defined"))
//            return date;
//
//        DateFormat format = new SimpleDateFormat(
//                getResources().getString(R.string.api_date_format), Locale.getDefault());
//        Date apiDate = null;
//        try {
//            apiDate = format.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        format = new SimpleDateFormat(
//                getResources().getString(R.string.date_format), Locale.getDefault());
//
//        return format.format(apiDate);
//    }

    private void setupPresenter() {
        mPresenter = new MoviePresenter(ApiFactory.buildMoviesService());
        mPresenter.addListener(this);
    }

    private void loadMovie(String movieId) {
        mMovieSubscription = mPresenter.loadMovie(getActivity(), movieId);
    }
}

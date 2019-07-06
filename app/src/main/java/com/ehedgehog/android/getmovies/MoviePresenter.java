package com.ehedgehog.android.getmovies;

import android.content.Context;

import com.ehedgehog.android.getmovies.model.Movie;
import com.ehedgehog.android.getmovies.network.MoviesService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MoviePresenter {

    public interface Listener {
        void onMovieLoaded(Movie movie);

        void onLoadingError(String message);
    }

    private Listener mListener;
    private final MoviesService mMoviesService;

    public MoviePresenter(MoviesService service) {
        mMoviesService = service;
    }

    public void addListener(MoviePresenter.Listener listener) {
        mListener = listener;
    }


    public Disposable loadMovie(Context context, String movieId) {
        return getMovie(movieId)
                .flatMap(movie -> {
                    Realm.init(context);
                    try (Realm realmInstance = Realm.getDefaultInstance()) {
                        Movie result = realmInstance.where(Movie.class)
                                .equalTo("mImdbId", movieId).findFirst();
                        if (result != null) {
                            movie = realmInstance.copyFromRealm(result);
                        } else {
                            Movie finalMovie = movie;
                            realmInstance.executeTransaction(realm -> realm.insert(finalMovie));
                        }
                    }
                    return Observable.just(movie);
                })
                .onErrorResumeNext(throwable -> {
                    Realm.init(context);
                    try (Realm realmInstance = Realm.getDefaultInstance()) {
                        Movie result = realmInstance.where(Movie.class)
                                .equalTo("mImdbId", movieId).findFirst();
                        if (result != null)
                            return Observable.just(realmInstance.copyFromRealm(result));
                        return Observable.empty();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mListener::onMovieLoaded,
                        throwable -> mListener.onLoadingError(throwable.getMessage()));
    }

    public Observable<Movie> getMovie(String movieId) {
        return mMoviesService.getMovie(movieId);
    }

}

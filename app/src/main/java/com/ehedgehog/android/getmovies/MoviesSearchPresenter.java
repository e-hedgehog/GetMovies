package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.util.Log;

import com.ehedgehog.android.getmovies.model.MoviesResponse;
import com.ehedgehog.android.getmovies.model.MoviesSearchResult;
import com.ehedgehog.android.getmovies.network.MoviesService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class MoviesSearchPresenter {

    private static final String TAG = "MoviesSearchPresenter";

    public interface Listener {
        void onMoviesFound(List<MoviesSearchResult> movies);

        void onSearchError(String message);
    }

    private Paginator mPaginator;
    private Listener mListener;
    private final MoviesService mMoviesService;

    public MoviesSearchPresenter(MoviesService service) {
        mMoviesService = service;
    }

    public void addListener(Listener listener) {
        mListener = listener;
    }

    public Disposable searchMovies(Context context, String s, String type, int page) {
        return getAllResults(s, type, page)
                .map(moviesResponse -> {
                    Log.i(TAG, "Status = " + moviesResponse.hasResponse());
                    Log.i(TAG, "total = " + moviesResponse.getTotalResults());
                    mPaginator.setupPaginator(moviesResponse.getTotalResults());
                    return moviesResponse.getMoviesSearchResults();
                })
                .flatMap(searchResults -> {
                    Realm.init(context);
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        if (page == 1)
                            realm.delete(MoviesSearchResult.class);
                        realm.insert(searchResults);
                    });
                    return Observable.just(searchResults);
                })
                .onErrorResumeNext(throwable -> {
                    Log.e(TAG, "Something is wrong", throwable);
                    Realm.init(context);
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<MoviesSearchResult> results =
                            realm.where(MoviesSearchResult.class).findAll();
                    return Observable.just(realm.copyFromRealm(results));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListener::onMoviesFound, throwable ->
                        mListener.onSearchError(throwable.getMessage()));
    }

    public Observable<MoviesResponse> getAllResults(String s, String type, int page) {
        Log.i(TAG, "page = " + page);
        return mMoviesService.searchMovies(s, type, page);
    }

    public Paginator getPaginator() {
        return mPaginator;
    }

    public void setPaginator(Paginator paginator) {
        mPaginator = paginator;
    }
}

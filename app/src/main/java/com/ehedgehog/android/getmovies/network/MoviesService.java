package com.ehedgehog.android.getmovies.network;


import com.ehedgehog.android.getmovies.model.Movie;
import com.ehedgehog.android.getmovies.model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("/")
    Observable<MoviesResponse> searchMovies(@Query("s") String s,
                                            @Query("type") String type,
                                            @Query("page") int page);

    @GET("/")
    Observable<Movie> getMovie(@Query("i") String id);

}

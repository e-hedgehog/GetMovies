package com.ehedgehog.android.getmovies;

import com.ehedgehog.android.getmovies.model.Movie;
import com.ehedgehog.android.getmovies.network.MoviesService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MoviePresenterTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public final RxSchedulersRule mSchedulersRule = new RxSchedulersRule();

    @Mock
    private MoviesService mMoviesService;

    @Mock
    private MoviePresenter.Listener mListener;

    @InjectMocks private MoviePresenter mPresenter;

    @Before
    public void setUp() {
        Movie movie = new Movie();
        when(mMoviesService.getMovie(anyString()))
                .thenReturn(Observable.just(movie));

        mPresenter.addListener(mListener);
    }

    @Test
    public void getMovie() {
        TestObserver<Movie> testObserver = mPresenter.getMovie("test").test();
        testObserver.awaitTerminalEvent();
        testObserver
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1);
    }
}

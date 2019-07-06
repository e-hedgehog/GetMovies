package com.ehedgehog.android.getmovies;

import com.ehedgehog.android.getmovies.model.MoviesResponse;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class MoviesSearchPresenterTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public final RxSchedulersRule mSchedulersRule = new RxSchedulersRule();

    @Mock
    private MoviesService mMoviesService;

    @Mock
    private MoviesSearchPresenter.Listener mListener;

    @InjectMocks private MoviesSearchPresenter mPresenter;

    private MoviesResponse mResponse;

    @Before
    public void setUp() {
        mResponse = new MoviesResponse();
        when(mMoviesService.searchMovies(anyString(), anyString(), anyInt()))
                .thenReturn(Observable.just(mResponse));

        mPresenter.addListener(mListener);
    }

    @Test
    public void searchMovies() {
        TestObserver<MoviesResponse> testObserver = mPresenter
                .getAllResults("test", "default", 1).test();
        testObserver.awaitTerminalEvent();
        testObserver
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1);
    }
}

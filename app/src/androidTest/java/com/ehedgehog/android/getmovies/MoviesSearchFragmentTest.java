package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MoviesSearchFragmentTest {

    @Rule
    public ActivityTestRule<MoviesSearchActivity> mActivityTestRule =
            new ActivityTestRule<>(MoviesSearchActivity.class);

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    private MoviesSearchFragment fragment;

    @Before
    public void setup() {
        fragment = new MoviesSearchFragment();
        mActivityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction().add(R.id.fragment_container, fragment).commit();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ehedgehog.android.getmovies", appContext.getPackageName());
    }

    @Test
    public void testFragment() {
        Fragment fragment = mActivityTestRule.getActivity()
                .getSupportFragmentManager().getFragments().get(0);
        assertTrue(fragment instanceof MoviesSearchFragment);
    }

    @Test
    public void testFragmentHasRecyclerView() {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.movies_recycler_view);
        assertTrue(recyclerView.isShown());
    }

    @Test
    public void testHolderHasItemView() {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.movies_recycler_view);
        MovieHolder holder = (MovieHolder) recyclerView.findViewHolderForAdapterPosition(1);

        assertNotEquals(holder, null);
        assertNotEquals(holder.itemView, null);
    }

    @Test
    public void testRecyclerViewItemClick() {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.movies_recycler_view);
        MovieHolder holder = (MovieHolder) recyclerView.findViewHolderForAdapterPosition(1);

        assertTrue(holder.itemView.isClickable());
    }

    @Test
    public void testHolderCardViewIsShown() {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.movies_recycler_view);
        MovieHolder holder = (MovieHolder) recyclerView.findViewHolderForAdapterPosition(1);

        assertTrue(holder.itemView.findViewById(R.id.movie_item_card).isShown());
    }

    @Test
    public void testHolderContentIsShown() {
        RecyclerView recyclerView = fragment.getView().findViewById(R.id.movies_recycler_view);
        MovieHolder holder = (MovieHolder) recyclerView.findViewHolderForAdapterPosition(1);

        assertTrue(holder.itemView.findViewById(R.id.movie_item_image).isShown());
        assertTrue(holder.itemView.findViewById(R.id.movie_item_title).isShown());
        assertTrue(holder.itemView.findViewById(R.id.movie_item_year).isShown());
    }

    @Test
    public void testSwipeRefreshIsShown() {
        SwipeRefreshLayout swipeRefreshLayout = fragment.getView()
                .findViewById(R.id.movies_refresh_container);
        assertTrue(swipeRefreshLayout.isShown());
    }

    @Test
    public void testSpinnerIsShown() {
        Spinner spinner = fragment.getView().findViewById(R.id.movies_type_spinner);
        assertTrue(spinner.isShown());
    }
}

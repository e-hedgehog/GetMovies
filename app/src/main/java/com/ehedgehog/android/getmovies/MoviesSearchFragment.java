package com.ehedgehog.android.getmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ehedgehog.android.getmovies.model.MoviesSearchResult;
import com.ehedgehog.android.getmovies.network.ApiFactory;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class MoviesSearchFragment extends Fragment {

    private static final String TAG = "MoviesSearchFragment";
//    private static final int ITEMS_PER_PAGE = 20;

    private TextView mEmptyView;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Spinner mTypeSpinner;

    private MoviesSearchPresenter mPresenter;
    private Paginator mPaginator;

    private String mType;
//    private int mCurrentPage;
    private boolean isLoading;

    private Disposable mMoviesSubscription;

    public static MoviesSearchFragment newInstance() {
        return new MoviesSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        isOnline();

        if (mPaginator != null)
            mPaginator.resetCurrentPage();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        mEmptyView = view.findViewById(R.id.movies_empty_view);

        setupMoviesPresenter();

        mRefreshLayout = view.findViewById(R.id.movies_refresh_container);
        setupSwipeRefresh();

        mTypeSpinner = view.findViewById(R.id.movies_type_spinner);
        setupTypeSpinner();

        mProgressBar = view.findViewById(R.id.movies_progress_bar);
        mRecyclerView = view.findViewById(R.id.movies_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemsCount = layoutManager.getChildCount();
                int invisibleItemsCount = layoutManager.findFirstVisibleItemPosition();
                int totalItemsCount = layoutManager.getItemCount();
                if ((visibleItemsCount + invisibleItemsCount) >= totalItemsCount) {
                    if ((mPaginator.getCurrentPage() <= mPaginator.getPagesCount()) && !isLoading) {
                        Log.i(TAG, "Loading new data...");
                        mPaginator.incrementCurrentPage();
                        searchMovies(mType, mPaginator.getCurrentPage());
                    }
                }
            }
        });

        searchMovies(mType, mPaginator.getCurrentPage());

        return view;
    }

    @Override
    public void onPause() {
        if (mMoviesSubscription != null)
            mMoviesSubscription.dispose();

        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_movies_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search_item);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() < 3)
                    return false;

                mPaginator.resetCurrentPage();
                MoviesPreferences.setStoredQuery(getActivity(), s.trim().toLowerCase());
                searchMovies(mType, mPaginator.getCurrentPage());
                return false;
            }
        });

        searchView.setOnSearchClickListener(v -> {
            String query = MoviesPreferences.getStoredQuery(getActivity());
            searchView.setQuery(query, false);
        });
    }

    private void searchMovies(String type, int currentPage) {
        String query = MoviesPreferences.getStoredQuery(getActivity());
        isLoading = true;
        mMoviesSubscription = mPresenter
                .searchMovies(getActivity(), query, type, currentPage);
    }

    private void setupMoviesPresenter() {
        mPresenter = new MoviesSearchPresenter(ApiFactory.buildMoviesService());
        mPaginator = new Paginator();
        mPresenter.setPaginator(mPaginator);
        mPresenter.addListener(new MoviesSearchPresenter.Listener() {
            @Override
            public void onMoviesFound(List<MoviesSearchResult> movies) {
                updateUI(movies);
            }

            @Override
            public void onSearchError(String message) {
                Log.e(TAG, message);
            }
        });
    }

    private void updateUI(List<MoviesSearchResult> movies) {
        MoviesAdapter adapter = (MoviesAdapter) mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new MoviesAdapter(getActivity(), movies);
            mRecyclerView.setAdapter(adapter);
        } else {
            if (mPaginator.getCurrentPage() == 1) {
                adapter.setMovies(movies);
                adapter.notifyDataSetChanged();
            } else {
                adapter.addAll(movies);
                adapter.notifyItemRangeInserted(mPaginator.getCurrentPage() *
                                Paginator.ITEMS_PER_PAGE, Paginator.ITEMS_PER_PAGE);
            }
        }

        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
        mEmptyView.setVisibility(movies.size() > 0 ? View.GONE : View.VISIBLE);
    }

    private boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected())
            return true;

        Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void setupSwipeRefresh() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.black,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_dark
        );
        mRefreshLayout.setOnRefreshListener(() -> {
            mRefreshLayout.setRefreshing(true);
            if (isOnline()) {
                mPaginator.resetCurrentPage();
                searchMovies(mType, mPaginator.getCurrentPage());
            }
            mRefreshLayout.setRefreshing(false);
        });
    }

    private void setupTypeSpinner() {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);

        String[] types = getActivity()
                .getResources().getStringArray(R.array.types_array);
        mType = types[MoviesPreferences.getStoredType(getActivity())];

        mTypeSpinner.setSelection(MoviesPreferences.getStoredType(getActivity()));
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = types[position];
                MoviesPreferences.setStoredType(getActivity(), position);
                mProgressBar.setVisibility(View.VISIBLE);

                mPaginator.resetCurrentPage();
                searchMovies(mType, mPaginator.getCurrentPage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

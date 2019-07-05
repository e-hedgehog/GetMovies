package com.ehedgehog.android.getmovies;

import android.support.v4.app.Fragment;

public class MoviesSearchActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return MoviesSearchFragment.newInstance();
    }
}

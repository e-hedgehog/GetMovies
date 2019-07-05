package com.ehedgehog.android.getmovies;

public class Paginator {

    public static final int ITEMS_PER_PAGE = 10;

    private int mCurrentPage;
    private int mPagesCount;
    private int mTotalItems;

    public Paginator() {
        mCurrentPage = 1;
    }

    public void setupPaginator(int totalItems) {
        mTotalItems = totalItems;
        mPagesCount = (int) Math.ceil(mTotalItems / ITEMS_PER_PAGE);
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void incrementCurrentPage() {
        mCurrentPage = mCurrentPage + 1;
    }

    public void resetCurrentPage() {
        mCurrentPage = 1;
    }

    public int getPagesCount() {
        return mPagesCount;
    }

    public int getTotalItems() {
        return mTotalItems;
    }
}

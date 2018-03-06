package com.nickwelna.popularmovies.networking;

/**
 * Created by Nick on 3/5/2018.
 */
public class MovieList {

    int page;
    int total_results;
    int total_pages;
    MovieListResult[] results;

    public int getPage() {

        return page;

    }

    public int getTotalResults() {

        return total_results;

    }

    public int getTotalPages() {

        return total_pages;

    }

    public MovieListResult[] getResults() {

        return results;

    }

}

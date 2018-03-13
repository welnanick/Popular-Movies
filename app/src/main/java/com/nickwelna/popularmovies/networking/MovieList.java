package com.nickwelna.popularmovies.networking;

import java.util.List;

public class MovieList {

    private int page;
    private int total_results;
    private int total_pages;
    private List<MovieListResult> results;

    public int getPage() {

        return page;

    }

    public int getTotalResults() {

        return total_results;

    }

    public int getTotalPages() {

        return total_pages;

    }

    public List<MovieListResult> getResults() {

        return results;

    }

}

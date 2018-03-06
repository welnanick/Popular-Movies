package com.nickwelna.popularmovies.networking;

/**
 * Created by Nick on 3/5/2018.
 */
class MovieListResult {

    String poster_path;
    boolean adult;
    String overview;
    String release_date;
    int[] genre_ids;
    int id;
    String original_title;
    String orignal_language;
    String title;
    String backdrop_path;
    double popularity;
    int vote_count;
    boolean video;
    double vote_average;

    public String getPosterPath() {

        return poster_path;

    }

    public boolean isAdult() {

        return adult;

    }

    public String getOverview() {

        return overview;

    }

    public String getReleaseDate() {

        return release_date;

    }

    public int[] getGenreIds() {

        return genre_ids;

    }

    public int getId() {

        return id;

    }

    public String getOriginalTitle() {

        return original_title;

    }

    public String getOrignalLanguage() {

        return orignal_language;

    }

    public String getTitle() {

        return title;

    }

    public String getBackdropPath() {

        return backdrop_path;

    }

    public double getPopularity() {

        return popularity;

    }

    public int getVoteCount() {

        return vote_count;

    }

    public boolean isVideo() {

        return video;

    }

    public double getVoteAverage() {

        return vote_average;

    }
}

package com.nickwelna.popularmovies.networking;

public class MovieListResult {

    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private int[] genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private double popularity;
    private int vote_count;
    private boolean video;
    private double vote_average;

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

    public String getOriginalLanguage() {

        return original_language;

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

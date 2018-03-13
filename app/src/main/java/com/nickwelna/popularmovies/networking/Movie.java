package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    private boolean adult;
    private String backdrop_path;
    private MovieCollection belongs_to_collection;
    private int budget;
    private List<Genre> genres;
    private String homepage;
    private int id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private String popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountries> production_countries;
    private String release_date;
    private int revenue;
    private int runtime;
    private List<SpokenLanguages> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    public boolean isAdult() {

        return adult;

    }

    public String getBackdropPath() {

        return backdrop_path;

    }

    public MovieCollection getBelongsToCollection() {

        return belongs_to_collection;

    }

    public int getBudget() {

        return budget;

    }

    public List<Genre> getGenres() {

        return genres;

    }

    public String getHomepage() {

        return homepage;

    }

    public int getId() {

        return id;

    }

    public String getImdbId() {

        return imdb_id;

    }

    public String getOriginalLanguage() {

        return original_language;

    }

    public String getOriginalTitle() {

        return original_title;

    }

    public String getOverview() {

        return overview;

    }

    public String getPopularity() {

        return popularity;

    }

    public String getPosterPath() {

        return poster_path;

    }

    public List<ProductionCompany> getProductionCompanies() {

        return production_companies;

    }

    public List<ProductionCountries> getProductionCountries() {

        return production_countries;

    }

    public String getReleaseDate() {

        return release_date;

    }

    public int getRevenue() {

        return revenue;

    }

    public int getRuntime() {

        return runtime;

    }

    public List<SpokenLanguages> getSpokenLanguages() {

        return spoken_languages;

    }

    public String getStatus() {

        return status;

    }

    public String getTagline() {

        return tagline;

    }

    public String getTitle() {

        return title;

    }

    public boolean isVideo() {

        return video;

    }

    public double getVoteAverage() {

        return vote_average;

    }

    public int getVoteCount() {

        return vote_count;

    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdrop_path);
        dest.writeParcelable(this.belongs_to_collection, flags);
        dest.writeInt(this.budget);
        dest.writeList(this.genres);
        dest.writeString(this.homepage);
        dest.writeInt(this.id);
        dest.writeString(this.imdb_id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.popularity);
        dest.writeString(this.poster_path);
        dest.writeList(this.production_companies);
        dest.writeList(this.production_countries);
        dest.writeString(this.release_date);
        dest.writeInt(this.revenue);
        dest.writeInt(this.runtime);
        dest.writeList(this.spoken_languages);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
        dest.writeString(this.title);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);

    }

    public Movie() {

    }

    private Movie(Parcel in) {

        this.adult = in.readByte() != 0;
        this.backdrop_path = in.readString();
        this.belongs_to_collection = in.readParcelable(MovieCollection.class.getClassLoader());
        this.budget = in.readInt();
        this.genres = new ArrayList<>();
        in.readList(this.genres, Genre.class.getClassLoader());
        this.homepage = in.readString();
        this.id = in.readInt();
        this.imdb_id = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.popularity = in.readString();
        this.poster_path = in.readString();
        this.production_companies = new ArrayList<>();
        in.readList(this.production_companies, ProductionCompany.class.getClassLoader());
        this.production_countries = new ArrayList<>();
        in.readList(this.production_countries, ProductionCountries.class.getClassLoader());
        this.release_date = in.readString();
        this.revenue = in.readInt();
        this.runtime = in.readInt();
        this.spoken_languages = new ArrayList<>();
        in.readList(this.spoken_languages, SpokenLanguages.class.getClassLoader());
        this.status = in.readString();
        this.tagline = in.readString();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {

            return new Movie(source);

        }

        @Override
        public Movie[] newArray(int size) {

            return new Movie[size];

        }

    };

}

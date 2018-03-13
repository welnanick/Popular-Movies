package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

class MovieCollection implements Parcelable {

    private int id;
    private String name;
    private String overview;
    private String poster_path;
    private String backdrop_path;

    public int getId() {

        return id;

    }

    public String getName() {

        return name;

    }

    public String getOverview() {

        return overview;

    }

    public String getPosterPath() {

        return poster_path;

    }

    public String getBackdropPath() {

        return backdrop_path;

    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);

    }

    public MovieCollection() {

    }

    MovieCollection(Parcel in) {

        this.id = in.readInt();
        this.name = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();

    }

    public static final Creator<MovieCollection> CREATOR = new Creator<MovieCollection>() {

        @Override
        public MovieCollection createFromParcel(Parcel source) {

            return new MovieCollection(source);

        }

        @Override
        public MovieCollection[] newArray(int size) {

            return new MovieCollection[size];

        }

    };

}

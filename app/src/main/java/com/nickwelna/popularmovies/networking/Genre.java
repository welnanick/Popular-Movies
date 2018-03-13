package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

class Genre implements Parcelable {

    private int id;
    private String name;

    public int getId() {

        return id;

    }

    public String getName() {

        return name;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeString(this.name);

    }

    public Genre() {

    }

    Genre(Parcel in) {

        this.id = in.readInt();
        this.name = in.readString();

    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {

        @Override
        public Genre createFromParcel(Parcel source) {

            return new Genre(source);

        }

        @Override
        public Genre[] newArray(int size) {

            return new Genre[size];

        }

    };

}

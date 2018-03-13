package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

class SpokenLanguages implements Parcelable {

    private String iso_639_1;
    private String name;

    public String getIso_639_1() {

        return iso_639_1;

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

        dest.writeString(this.iso_639_1);
        dest.writeString(this.name);

    }

    public SpokenLanguages() {

    }

    SpokenLanguages(Parcel in) {

        this.iso_639_1 = in.readString();
        this.name = in.readString();

    }

    public static final Creator<SpokenLanguages> CREATOR = new Creator<SpokenLanguages>() {

        @Override
        public SpokenLanguages createFromParcel(Parcel source) {

            return new SpokenLanguages(source);

        }

        @Override
        public SpokenLanguages[] newArray(int size) {

            return new SpokenLanguages[size];

        }

    };

}

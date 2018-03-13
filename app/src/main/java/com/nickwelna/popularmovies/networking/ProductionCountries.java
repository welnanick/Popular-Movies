package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

class ProductionCountries implements Parcelable {

    private String iso_3166_1;
    private String name;

    public String getIso_3166_1() {

        return iso_3166_1;

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

        dest.writeString(this.iso_3166_1);
        dest.writeString(this.name);

    }

    public ProductionCountries() {

    }

    ProductionCountries(Parcel in) {

        this.iso_3166_1 = in.readString();
        this.name = in.readString();

    }

    public static final Creator<ProductionCountries> CREATOR = new Creator<ProductionCountries>() {

        @Override
        public ProductionCountries createFromParcel(Parcel source) {

            return new ProductionCountries(source);

        }

        @Override
        public ProductionCountries[] newArray(int size) {

            return new ProductionCountries[size];

        }

    };

}

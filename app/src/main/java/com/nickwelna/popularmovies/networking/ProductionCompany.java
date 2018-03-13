package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

class ProductionCompany implements Parcelable {

    private String name;
    private int id;

    public String getName() {

        return name;

    }

    public int getId() {

        return id;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.name);
        dest.writeInt(this.id);

    }

    public ProductionCompany() {

    }

    ProductionCompany(Parcel in) {

        this.name = in.readString();
        this.id = in.readInt();

    }

    public static final Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {

        @Override
        public ProductionCompany createFromParcel(Parcel source) {

            return new ProductionCompany(source);

        }

        @Override
        public ProductionCompany[] newArray(int size) {

            return new ProductionCompany[size];

        }

    };

}

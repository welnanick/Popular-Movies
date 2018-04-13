package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ReviewList implements Parcelable {

    private int id;
    private int page;
    private List<Review> results;

    public int getId() {

        return id;

    }

    public int getPage() {

        return page;

    }

    public List<Review> getResults() {

        return results;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeInt(this.page);
        dest.writeList(this.results);

    }

    public ReviewList() {

    }

    protected ReviewList(Parcel in) {

        this.id = in.readInt();
        this.page = in.readInt();
        this.results = new ArrayList<>();
        in.readList(this.results, Review.class.getClassLoader());

    }

    public static final Creator<ReviewList> CREATOR = new Creator<ReviewList>() {

        @Override
        public ReviewList createFromParcel(Parcel source) {

            return new ReviewList(source);

        }

        @Override
        public ReviewList[] newArray(int size) {

            return new ReviewList[size];

        }

    };

}

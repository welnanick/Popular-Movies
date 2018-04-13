package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    private String author;
    private String content;
    private String id;
    private String url;

    public String getAuthor() {

        return author;

    }

    public String getContent() {

        return content.replace("\r", "");

    }

    public String getId() {

        return id;

    }

    public String getUrl() {

        return url;
    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.id);
        dest.writeString(this.url);

    }

    public Review() {

    }

    protected Review(Parcel in) {

        this.author = in.readString();
        this.content = in.readString();
        this.id = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {

        @Override
        public Review createFromParcel(Parcel source) {

            return new Review(source);

        }

        @Override
        public Review[] newArray(int size) {

            return new Review[size];

        }

    };

}

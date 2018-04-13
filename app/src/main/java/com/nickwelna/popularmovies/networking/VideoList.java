package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoList implements Parcelable {

    private int id;
    private List<Video> results;

    public int getId() {

        return id;

    }

    public List<Video> getResults() {

        return results;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeTypedList(this.results);

    }

    public VideoList() {

    }

    protected VideoList(Parcel in) {

        this.id = in.readInt();
        this.results = in.createTypedArrayList(Video.CREATOR);

    }

    public static final Creator<VideoList> CREATOR = new Creator<VideoList>() {

        @Override
        public VideoList createFromParcel(Parcel source) {

            return new VideoList(source);

        }

        @Override
        public VideoList[] newArray(int size) {

            return new VideoList[size];

        }

    };

}

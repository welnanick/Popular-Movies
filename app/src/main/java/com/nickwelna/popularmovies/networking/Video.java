package com.nickwelna.popularmovies.networking;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable {

    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    public String getId() {

        return id;

    }

    public String getIso6391() {

        return iso_639_1;

    }

    public String getIso31661() {

        return iso_3166_1;

    }

    public String getKey() {

        return key;

    }

    public String getName() {

        return name;

    }

    public String getSite() {

        return site;

    }

    public int getSize() {

        return size;

    }

    public String getType() {

        return type;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.id);
        dest.writeString(this.iso_639_1);
        dest.writeString(this.iso_3166_1);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.type);

    }

    public Video() {

    }

    protected Video(Parcel in) {

        this.id = in.readString();
        this.iso_639_1 = in.readString();
        this.iso_3166_1 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.type = in.readString();

    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {

        @Override
        public Video createFromParcel(Parcel source) {

            return new Video(source);

        }

        @Override
        public Video[] newArray(int size) {

            return new Video[size];

        }

    };

}

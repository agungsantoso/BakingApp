package com.agungsantoso.udacity.bakingapp.data;

/**
 * Created by asanpra on 13/09/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asanpra on 13/09/2017.
 */

public class StepsParcel
        implements Parcelable {

    //http://www.vogella.com/tutorials/AndroidParcelable/article.html
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StepsParcel createFromParcel(Parcel in) {
            return new StepsParcel(in);
        }

        public StepsParcel[] newArray(int size) {
            return new StepsParcel[size];
        }
    };

    String id;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public StepsParcel(
            String id,
            String shortDescription,
            String description,
            String videoURL,
            String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public StepsParcel(Parcel in) {
        this.id = in.readString();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(
            Parcel dest,
            int flags
    ) {
        dest.writeString(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "parcel";
    }
}


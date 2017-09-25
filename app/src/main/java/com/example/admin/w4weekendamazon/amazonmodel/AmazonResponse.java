
package com.example.admin.w4weekendamazon.amazonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AmazonResponse implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageURL")
    @Expose
    private String imageURL;
    @SerializedName("author")
    @Expose
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.imageURL);
        dest.writeString(this.author);
    }

    public AmazonResponse() {
    }

    protected AmazonResponse(Parcel in) {
        this.title = in.readString();
        this.imageURL = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<AmazonResponse> CREATOR = new Parcelable.Creator<AmazonResponse>() {
        @Override
        public AmazonResponse createFromParcel(Parcel source) {
            return new AmazonResponse(source);
        }

        @Override
        public AmazonResponse[] newArray(int size) {
            return new AmazonResponse[size];
        }
    };
}

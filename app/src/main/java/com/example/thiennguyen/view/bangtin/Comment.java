// File: Comment.java
package com.example.thiennguyen.view.bangtin;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    public String author;
    public String content;
    public String time;
    public int avatarRes;

    public Comment(String author, String content, String time, int avatarRes) {
        this.author = author;
        this.content = content;
        this.time = time;
        this.avatarRes = avatarRes;
    }

    protected Comment(Parcel in) {
        author = in.readString();
        content = in.readString();
        time = in.readString();
        avatarRes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(time);
        dest.writeInt(avatarRes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
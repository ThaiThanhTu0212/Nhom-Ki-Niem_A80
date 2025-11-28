package com.example.thiennguyen.view.bangtin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class NewsPost implements Parcelable {
    public String username, time, content;
    public int imgMain, imgSmall1, imgSmall2;
    public int likeCount = 0;
    public boolean isLiked = false;
    public int commentCount = 0;
    public ArrayList<Comment> comments; // nếu bạn có class Comment

    public NewsPost(String username, String time, String content, int imgMain, int imgSmall1, int imgSmall2) {
        this.username = username;
        this.time = time;
        this.content = content;
        this.imgMain = imgMain;
        this.imgSmall1 = imgSmall1;
        this.imgSmall2 = imgSmall2;
    }

    protected NewsPost(Parcel in) {
        username = in.readString();
        time = in.readString();
        content = in.readString();
        imgMain = in.readInt();
        imgSmall1 = in.readInt();
        imgSmall2 = in.readInt();
        likeCount = in.readInt();
        isLiked = in.readByte() != 0;
        commentCount = in.readInt();
        comments = in.createTypedArrayList(Comment.CREATOR); // nếu có Comment
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(time);
        dest.writeString(content);
        dest.writeInt(imgMain);
        dest.writeInt(imgSmall1);
        dest.writeInt(imgSmall2);
        dest.writeInt(likeCount);
        dest.writeByte((byte) (isLiked ? 1 : 0));
        dest.writeInt(commentCount);
        dest.writeTypedList(comments);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewsPost> CREATOR = new Creator<NewsPost>() {
        @Override
        public NewsPost createFromParcel(Parcel in) {
            return new NewsPost(in);
        }

        @Override
        public NewsPost[] newArray(int size) {
            return new NewsPost[size];
        }
    };
}
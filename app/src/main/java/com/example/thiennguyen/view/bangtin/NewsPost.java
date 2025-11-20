// File: NewsPost.java
package com.example.thiennguyen.view.bangtin;

import java.util.ArrayList;

public class NewsPost {
    public String username;
    public String time;
    public String content;
    public int imgMain;
    public int imgSmall1;
    public int imgSmall2;

    public int likeCount = 0;
    public boolean isLiked = false;
    public int commentCount = 0;

    // QUAN TRỌNG: KHỞI TẠO LUÔN ĐỂ TRÁNH NULL POINTER
    public ArrayList<Comment> comments = new ArrayList<>();

    public NewsPost(String username, String time, String content,
                    int imgMain, int imgSmall1, int imgSmall2) {
        this.username = username;
        this.time = time;
        this.content = content;
        this.imgMain = imgMain;
        this.imgSmall1 = imgSmall1;
        this.imgSmall2 = imgSmall2;
    }
}
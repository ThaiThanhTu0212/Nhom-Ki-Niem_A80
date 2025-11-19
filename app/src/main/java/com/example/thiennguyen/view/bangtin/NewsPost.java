package com.example.thiennguyen.view.bangtin;

public class NewsPost {
    public String username;
    public String time;
    public String content;
    public int imgMain;
    public int imgSmall1;
    public int imgSmall2;

    // THÊM 2 FIELD MỚI
    public int likeCount = 0;        // số lượt thích
    public boolean isLiked = false;  // đã thích chưa

    public NewsPost(String username, String time, String content, int imgMain, int imgSmall1, int imgSmall2) {
        this.username = username;
        this.time = time;
        this.content = content;
        this.imgMain = imgMain;
        this.imgSmall1 = imgSmall1;
        this.imgSmall2 = imgSmall2;
    }
}
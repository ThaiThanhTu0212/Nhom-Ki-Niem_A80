package com.example.thiennguyen.api.bangtin;

import java.io.Serializable;

public class NewsPost implements Serializable {
    public int id;
    public String author;
    public String time;
    public String content;
    public String imageUrl;
    public int avatarResource;
    public int commentCount;
    public int likeCount;
    public boolean isLiked;

    // Các trường mới cho thanh ủng hộ
    public boolean showDonationBar;
    public int donationProgress;
    public int donatorsCount;
    public int daysLeft;

    // Constructor cũ (giữ lại để tương thích nếu cần)
    public NewsPost(int id, String author, String time, String content, String imageUrl, int avatarResource, int likeCount, int commentCount) {
        this(id, author, time, content, imageUrl, avatarResource, likeCount, commentCount, false, 0, 0, 0);
    }

    // Constructor mới đầy đủ
    public NewsPost(int id, String author, String time, String content, String imageUrl, int avatarResource, int likeCount, int commentCount, boolean showDonationBar, int donationProgress, int donatorsCount, int daysLeft) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.content = content;
        this.imageUrl = imageUrl;
        this.avatarResource = avatarResource;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLiked = false;
        this.showDonationBar = showDonationBar;
        this.donationProgress = donationProgress;
        this.donatorsCount = donatorsCount;
        this.daysLeft = daysLeft;
    }
}

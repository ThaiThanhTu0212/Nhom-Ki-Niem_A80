package com.example.thiennguyen.api.bangtin;

import java.io.Serializable;

public class NewsPost implements Serializable {
    public int id;
    public Integer campaignId;
    public String author;
    public String time;
    public String content;
    public String imageUrl;
    public int avatarResource;
    public int likeCount;
    public int commentCount;
    public boolean isLiked;
    public boolean showDonation;
    public int donationProgress;
    public int donatorsCount;
    public int daysLeft;

    // Các thuộc tính mới được thêm vào
    public boolean isSaved = false; // Bài viết đã được lưu chưa
    public boolean isFollowingAuthor = false; // Đã theo dõi tác giả chưa

    public NewsPost(int id, Integer campaignId, String author, String time, String content, String imageUrl, int avatarResource, int likeCount, int commentCount, boolean showDonation, int donationProgress, int donatorsCount, int daysLeft) {
        this.id = id;
        this.campaignId = campaignId;
        this.author = author;
        this.time = time;
        this.content = content;
        this.imageUrl = imageUrl;
        this.avatarResource = avatarResource;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.showDonation = showDonation;
        this.donationProgress = donationProgress;
        this.donatorsCount = donatorsCount;
        this.daysLeft = daysLeft;
        this.isLiked = false; // Mặc định là chưa thích
    }
}

package com.example.thiennguyen.api.bangtin;

import java.io.Serializable;

public class NewsPost implements Serializable {
    public int id; // THÊM MỚI: ID của bài viết
    public String author;
    public String time;
    public String content;
    public String imageUrl;
    public int avatarResource;
    public int commentCount;
    public int likeCount;
    public boolean isLiked;

    // Constructor đã được cập nhật
    public NewsPost(int id, String author, String time, String content, String imageUrl, int avatarResource) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.content = content;
        this.imageUrl = imageUrl;
        this.avatarResource = avatarResource;
        this.commentCount = 0;
        this.likeCount = 0;
        this.isLiked = false;
    }
}

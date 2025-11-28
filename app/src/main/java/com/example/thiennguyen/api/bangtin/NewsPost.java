package com.example.thiennguyen.api.bangtin;

import java.io.Serializable;

public class NewsPost implements Serializable {
    public int id;
    public String author;
    public String time;
    public String content;
    public String imageUrl;
    public int avatarResource;
    public int commentCount; // Giữ nguyên
    public int likeCount;    // Giữ nguyên
    public boolean isLiked;

    public NewsPost(int id, String author, String time, String content, String imageUrl, int avatarResource, int likeCount, int commentCount) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.content = content;
        this.imageUrl = imageUrl;
        this.avatarResource = avatarResource;
        this.likeCount = likeCount; // Gán giá trị từ server
        this.commentCount = commentCount; // Gán giá trị từ server
        this.isLiked = false; // Mặc định là chưa thích
    }
}

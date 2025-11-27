package com.example.thiennguyen.api.bangtin;

import java.io.Serializable;

// Lớp này là một POJO (Plain Old Java Object) để đóng gói dữ liệu của một bài đăng.
// Implement Serializable để có thể truyền đối tượng này giữa các Activity thông qua Intent.
public class NewsPost implements Serializable {
    public String author;
    public String time;
    public String content;
    public int imageResource;
    public int avatarResource;
    public int likeIconResource;
    public int commentCount; // Thêm trường này để lưu số lượng bình luận

    public NewsPost(String author, String time, String content, int imageResource, int avatarResource, int likeIconResource) {
        this.author = author;
        this.time = time;
        this.content = content;
        this.imageResource = imageResource;
        this.avatarResource = avatarResource;
        this.likeIconResource = likeIconResource;
        this.commentCount = 0; // Khởi tạo số bình luận là 0
    }
}

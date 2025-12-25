package com.example.thiennguyen.view.bangtin;

// Lớp này dùng để biểu diễn một đối tượng bình luận trên giao diện người dùng (UI model)
public class BinhLuan {

    public String author;
    public String content;
    public String time;
    public int avatarRes;

    // Constructor mà BangTinCommentActivity đang sử dụng
    public BinhLuan(String author, String content, String time, int avatarRes) {
        this.author = author;
        this.content = content;
        this.time = time;
        this.avatarRes = avatarRes;
    }

    // Getters để Adapter có thể lấy dữ liệu
    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getAvatarRes() {
        return avatarRes;
    }
}

package com.example.thiennguyen.api.bangtin;

// DTO để gửi dữ liệu khi tạo một bình luận mới
public class CommentRequest {
    private int IdNguoiBinhLuan;
    private String NoiDung;

    public CommentRequest(int idNguoiBinhLuan, String noiDung) {
        this.IdNguoiBinhLuan = idNguoiBinhLuan;
        this.NoiDung = noiDung;
    }
}

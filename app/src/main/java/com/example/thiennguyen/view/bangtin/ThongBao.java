package com.example.thiennguyen.view.bangtin;

public class ThongBao {
    private String content;
    private int avatarResId;

    public ThongBao(String content, int avatarResId) {
        this.content = content;
        this.avatarResId = avatarResId;
    }

    public String getContent() {
        return content;
    }

    public int getAvatarResId() {
        return avatarResId;
    }
}

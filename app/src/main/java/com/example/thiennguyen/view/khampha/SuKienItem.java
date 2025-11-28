package com.example.thiennguyen.view.khampha;

public class SuKienItem {

    private final int bannerRes;
    private final String tag;
    private final String title;
    private final String date;
    private final String interested;

    public SuKienItem(int bannerRes, String tag, String title, String date, String interested) {
        this.bannerRes = bannerRes;
        this.tag = tag;
        this.title = title;
        this.date = date;
        this.interested = interested;
    }

    public int getBannerRes() { return bannerRes; }
    public String getTag() { return tag; }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getInterested() { return interested; }
}

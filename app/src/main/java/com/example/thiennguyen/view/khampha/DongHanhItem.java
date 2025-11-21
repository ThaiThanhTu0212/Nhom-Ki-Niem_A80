package com.example.thiennguyen.view.khampha;

public class DongHanhItem {

    private final int imageRes;
    private final String title;
    private final String amount;
    private final String startDate;
    private final String sponsor;
    private final int rankIcon;

    public DongHanhItem(int imageRes, String title, String amount, String startDate,
                        String sponsor, int rankIcon) {
        this.imageRes = imageRes;
        this.title = title;
        this.amount = amount;
        this.startDate = startDate;
        this.sponsor = sponsor;
        this.rankIcon = rankIcon;
    }

    public int getImageRes() { return imageRes; }
    public String getTitle() { return title; }
    public String getAmount() { return amount; }
    public String getStartDate() { return startDate; }
    public String getSponsor() { return sponsor; }
    public int getRankIcon() { return rankIcon; }
}

package com.example.thiennguyen.view.khampha;

public class BannerItem {
    private final String title;
    private final String subtitle;
    private final int colorStart;
    private final int colorEnd;
    private final int iconRes;

    public BannerItem(String title, String subtitle, int colorStart, int colorEnd, int iconRes) {
        this.title = title;
        this.subtitle = subtitle;
        this.colorStart = colorStart;
        this.colorEnd = colorEnd;
        this.iconRes = iconRes;
    }

    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public int getColorStart() { return colorStart; }
    public int getColorEnd() { return colorEnd; }
    public int getIconRes() { return iconRes; }
}

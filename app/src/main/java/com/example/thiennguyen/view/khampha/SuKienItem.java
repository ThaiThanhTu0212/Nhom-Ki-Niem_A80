package com.example.thiennguyen.view.khampha;

import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Dữ liệu demo tất cả sự kiện.
     */
    public static List<SuKienItem> createSampleList() {
        List<SuKienItem> list = new ArrayList<>();

        list.add(new SuKienItem(
                R.drawable.khampha_sukien1,
                "Trẻ em vùng cao",
                "Giải đấu Pickleball Ước Mơ Xanh",
                "30/11/2025 - 30/11/2025",
                "234 người quan tâm"
        ));

        list.add(new SuKienItem(
            R.drawable.khampha_sukien2,
            "Hoàn cảnh khó khăn",
            "Kỷ niệm 20 năm thành lập PanNature",
            "05/01/2026",
            "20 người quan tâm"
        ));

        return list;
    }

    /**
     * Lấy danh sách sự kiện nổi bật (ví dụ: phần tử đầu tiên).
     */
    public static List<SuKienItem> createFeaturedList() {
        List<SuKienItem> all = createSampleList();
        List<SuKienItem> featured = new ArrayList<>();
        if (!all.isEmpty()) {
            featured.add(all.get(0));
        }
        return featured;
    }
}

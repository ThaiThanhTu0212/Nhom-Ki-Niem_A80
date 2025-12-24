package com.example.thiennguyen.view.khampha;

import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class DongHanhItem {

    private final int imageRes;
    private final String title;
    private final String amount;
    private final String startDate;
    private final String sponsor;
    private final int rank;

    public DongHanhItem(int imageRes, String title, String amount, String startDate,
                        String sponsor, int rank) {

        this.imageRes = imageRes;
        this.title = title;
        this.amount = amount;
        this.startDate = startDate;
        this.sponsor = sponsor;
        this.rank = rank;
    }

    public int getImageRes() { return imageRes; }
    public String getTitle() { return title; }
    public String getAmount() { return amount; }
    public String getStartDate() { return startDate; }
    public String getSponsor() { return sponsor; }
    public int getRank() { return rank; }

    public static List<DongHanhItem> createSampleList() {
        List<DongHanhItem> list = new ArrayList<>();

        list.add(new DongHanhItem(
                R.drawable.h1,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "5.981.152.110 đ",
                "24/04/2025",
                "MB - Khối CNTT",
                1   // TOP 1
        ));

        list.add(new DongHanhItem(
                R.drawable.h2,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "506.243.000 đ",
                "09/08/2025",
                "MB ĐÔNG SÀI GÒN",
                2   // TOP 2
        ));

        list.add(new DongHanhItem(
                R.drawable.h3,
                "Chương trình trồng 1 triệu cây xanh cho Trường Sa",
                "343.863.336 đ",
                "05/08/2025",
                "MB MỸ ĐÌNH",
                3   // TOP 3
        ));

        return list;
    }
}

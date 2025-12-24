package com.example.thiennguyen.view.khampha;

import com.example.thiennguyen.R;

import java.util.ArrayList;
import java.util.List;

public class HoanCanhItem {

    private final int imageRes;
    private final String title;
    private final String tag;
    private final String location;

    public HoanCanhItem(int imageRes, String title, String tag, String location) {
        this.imageRes = imageRes;
        this.title = title;
        this.tag = tag;
        this.location = location;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Tạo danh sách dữ liệu mẫu dùng chung cho KhamPhaFragment và HoanCanhActivity.
     */
    public static List<HoanCanhItem> createSampleList() {
        List<HoanCanhItem> list = new ArrayList<>();

        list.add(new HoanCanhItem(
                R.drawable.khampha_hoancanh1,
                "Hoàn cảnh chị Nguyễn Thị Mộng...",
                "Người nghèo",
                "397/1 Đường Ấp Thạnh Quí A, Xã Bình…"
        ));

        list.add(new HoanCanhItem(
                R.drawable.khampha_hoancanh2,
                "Hoàn cảnh cô Phạm Thị Vọng…",
                "Người nghèo",
                "Phú Khánh, Xã Phú Khánh, Huyện…"
        ));

        list.add(new HoanCanhItem(
                R.drawable.khampha_hoancanh3,
                "Hoàn cảnh Trần Thị Ngọc Em",
                "Bệnh hiểm nghèo",
                "Tượng Đài Thạnh Phước, Xã Thạnh…"
        ));

        return list;
    }
}


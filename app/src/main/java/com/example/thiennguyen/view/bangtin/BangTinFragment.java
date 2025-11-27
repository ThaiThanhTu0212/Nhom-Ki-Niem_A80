package com.example.thiennguyen.view.bangtin;

import com.example.thiennguyen.api.bangtin.*;
import android.app.Activity;                    // THÊM DÒNG NÀY
import android.content.Intent;                 // ĐÃ CÓ
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.thiennguyen.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.AlertDialog;
import android.widget.EditText;

public class BangTinFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsPostAdapter adapter;
    private ArrayList<NewsPost> postList;   // ĐÃ SỬA: dùng ArrayList thay List

    // NHẬN KẾT QUẢ TỪ COMMENT ACTIVITY
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {  // ĐÃ FIX: dùng Activity.RESULT_OK
            int position = data.getIntExtra("post_position", -1);
            int newCount = data.getIntExtra("new_comment_count", 0);
            if (position != -1 && position < postList.size()) {
                postList.get(position).commentCount = newCount;
                adapter.notifyItemChanged(position);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bang_tin, container, false);

        // === RecyclerView ===
        recyclerView = view.findViewById(R.id.bangtin_recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        adapter = new NewsPostAdapter(postList);
        recyclerView.setAdapter(adapter);

        // === GIỮ NGUYÊN: Click vào bài viết → mở bình luận ===
        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(getActivity(), BangTinCommentActivity.class);
            intent.putExtra("post_position", position);
            intent.putExtra("post_data", postList.get(position));
            startActivityForResult(intent, 100);
        });

        // === LOAD DỮ LIỆU THẬT TỪ API ===
        loadPostsFromServer();

        // === NÚT + → MỞ DIALOG TẠO BÀI THẬT ===
        ImageView fabCreatePost = view.findViewById(R.id.bangtin_fab_create_post);
        fabCreatePost.setOnClickListener(v -> showCreatePostDialog());

        // === NÚT THÔNG BÁO (GIỮ NGUYÊN) ===
        ImageView btnNotification = view.findViewById(R.id.bangtin_btn_notification);
        btnNotification.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThongBaoActivity.class);
            startActivity(intent);
        });

        // === TABLAYOUT (GIỮ NGUYÊN) ===
        TabLayout tabLayout = view.findViewById(R.id.bangtin_tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {}
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;

    }
    // LOAD BÀI VIẾT THẬT TỪ API
    private void loadPostsFromServer() {
        // Thêm kiểm tra null cho service
        if (RetrofitClient.getService() == null) {
            Toast.makeText(getContext(), "Không khởi tạo được API", Toast.LENGTH_LONG).show();
            return;
        }

        RetrofitClient.getService().getBaiViets().enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                if (!isAdded()) return; // tránh crash nếu Fragment đã destroy

                if (response.isSuccessful() && response.body() != null) {
                    postList.clear();
                    for (BaiViet bv : response.body()) {
                        String thoiGian = "Vừa xong";
                        if (bv.ngayDang != null) {
                            String raw = bv.ngayDang.toString();
                            if (raw.contains("T")) {
                                raw = raw.replace("T", " ").split("\\.")[0];
                                try {
                                    thoiGian = raw.substring(8, 10) + "/" +
                                            raw.substring(5, 7) + "/" +
                                            raw.substring(0, 4) + " " +
                                            raw.substring(11, 16);
                                } catch (Exception e) {
                                    thoiGian = "Vừa xong";
                                }
                            }
                        }

                        String noiDung = bv.noiDung != null ? bv.noiDung : "";

                        NewsPost post = new NewsPost(
                                "Người dùng " + bv.idNguoiDang,
                                thoiGian,
                                noiDung,
                                R.drawable.bangtin_img_default_post,
                                R.drawable.bangtin_img_default_post,
                                R.drawable.bangtin_img_default_post
                        );
                        postList.add(0, post);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Lỗi server: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BaiViet>> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // DIALOG TẠO BÀI VIẾT
    private void showCreatePostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_post, null);
        EditText edt = dialogView.findViewById(R.id.edt_create_post_content);

        builder.setView(dialogView)
                .setTitle("Chia sẻ điều gì đó...")
                .setPositiveButton("Đăng", (d, w) -> {
                    String content = edt.getText().toString().trim();
                    if (!content.isEmpty()) createPostOnServer(1, content);
                    else Toast.makeText(getContext(), "Nhập nội dung đi nào!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // GỬI BÀI LÊN SERVER
    private void createPostOnServer(int idNguoiDang, String noiDung) {
        CreatePostRequest req = new CreatePostRequest(idNguoiDang, noiDung);
        RetrofitClient.getService().createBaiViet(req).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Đăng thành công!", Toast.LENGTH_SHORT).show();
                    loadPostsFromServer(); // reload ngay
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
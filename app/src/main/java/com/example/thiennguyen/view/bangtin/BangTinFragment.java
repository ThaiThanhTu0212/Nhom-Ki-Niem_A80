package com.example.thiennguyen.view.bangtin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.BaiViet;
import com.example.thiennguyen.api.bangtin.CreatePostRequest;
import com.example.thiennguyen.api.bangtin.CreatePostResponse;
import com.example.thiennguyen.api.bangtin.LikeCommentResponse;
import com.example.thiennguyen.api.bangtin.NewsPost;
import com.example.thiennguyen.api.bangtin.NewsPostAdapter;
import com.example.thiennguyen.api.bangtin.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BangTinFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsPostAdapter adapter;
    private ArrayList<NewsPost> displayedPostList;
    private ArrayList<NewsPost> allPosts;
    private Uri selectedImageUri;
    private ImageView dialogImgSelectedPhoto;
    private ImageView dialogBtnRemovePhoto;
    private TabLayout tabLayout;
    private TextView tvEmptyView;

    private static final String IMAGE_BASE_URL = "http://192.168.120.213:5089"; // THAY BẰNG IP CỦA BẠN

    // --- ActivityResultLauncher THAY THẾ CHO onActivityResult --- 
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<Intent> commentLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerActivityLaunchers();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bang_tin, container, false);

        setupViews(view);
        setupRecyclerView();
        setupTabs(view);
        setupClickListeners(view);

        loadPostsFromServer(); // Chuyển sang load từ server

        return view;
    }

    private void registerActivityLaunchers() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (dialogImgSelectedPhoto != null && dialogBtnRemovePhoto != null && selectedImageUri != null) {
                            dialogImgSelectedPhoto.setImageURI(selectedImageUri);
                            dialogImgSelectedPhoto.setVisibility(View.VISIBLE);
                            dialogBtnRemovePhoto.setVisibility(View.VISIBLE);
                        }
                    }
                });

        commentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        int position = result.getData().getIntExtra("post_position", -1);
                        // Cập nhật số lượng bình luận nếu cần
                        if (position != -1 && position < displayedPostList.size()) {
                            int newCommentCount = result.getData().getIntExtra("new_comment_count", displayedPostList.get(position).commentCount);
                            if(newCommentCount > displayedPostList.get(position).commentCount){
                                displayedPostList.get(position).commentCount = newCommentCount;
                                adapter.notifyItemChanged(position, "comment_count_changed");
                            }
                        }
                    }
                });
    }

    private void setupViews(View view) {
        recyclerView = view.findViewById(R.id.bangtin_recyclerViewNews);
        tvEmptyView = view.findViewById(R.id.bangtin_tv_empty_view);
        tabLayout = view.findViewById(R.id.bangtin_tabLayout);
    }

    private void setupRecyclerView() {
        allPosts = new ArrayList<>();
        displayedPostList = new ArrayList<>();
        adapter = new NewsPostAdapter(displayedPostList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    
    private void setupTabs(View view) {
        if (tabLayout.getTabCount() == 0) { // Chỉ thêm tab nếu chưa có
            tabLayout.addTab(tabLayout.newTab().setText("Mới nhất"));
            tabLayout.addTab(tabLayout.newTab().setText("Nổi bật"));
            tabLayout.addTab(tabLayout.newTab().setText("Đang theo dõi"));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAndShowPosts(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setupClickListeners(View view) {
         adapter.setOnItemClickListener(new NewsPostAdapter.OnItemClickListener() {
            @Override
            public void onCommentClick(int position) {
                handleCommentClick(position);
            }

            @Override
            public void onLikeClick(int position) {
                handleLikeClick(position);
            }

            @Override
            public void onShareClick(int position) {
                handleShareClick(position);
            }

            @Override
            public void onMoreOptionsClick(int position, View anchorView) {
                showPostOptionsMenu(position, anchorView);
            }

            @Override
            public void onDonateClick(int position) {
                handleDonateClick(position);
            }
        });

        view.findViewById(R.id.bangtin_fab_create_post).setOnClickListener(v -> showCreatePostDialog());
        view.findViewById(R.id.bangtin_btn_notification).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThongBaoActivity.class);
            startActivity(intent);
        });
    }

    private void filterAndShowPosts(int tabPosition) {
        displayedPostList.clear();
        String emptyMessage = "";

        switch (tabPosition) {
            case 0: // Mới nhất
                displayedPostList.addAll(allPosts);
                emptyMessage = "Chưa có bài viết nào.";
                break;
            case 1: // Nổi bật
                List<NewsPost> trendingPosts = allPosts.stream()
                        .filter(p -> p.likeCount > 50) // Giảm ngưỡng để dễ test
                        .collect(Collectors.toList());
                displayedPostList.addAll(trendingPosts);
                emptyMessage = "Chưa có bài viết nào nổi bật.";
                break;
            case 2: // Đang theo dõi
                 emptyMessage = "Bạn chưa theo dõi một cá nhân hay tổ chức nào.";
                // Ví dụ lọc bài viết của người/tổ chức bạn theo dõi (cần có logic theo dõi)
                // List<NewsPost> followingPosts = allPosts.stream()
                //        .filter(p -> isFollowing(p.author))
                //        .collect(Collectors.toList());
                // displayedPostList.addAll(followingPosts);
                break;
        }

        if (displayedPostList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
            tvEmptyView.setText(emptyMessage);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

        adapter.notifyDataSetChanged();
    }

    private void loadPostsFromServer() { 
        if (RetrofitClient.getService() == null || getContext() == null) {
            Toast.makeText(getContext(), "Không khởi tạo được API", Toast.LENGTH_LONG).show();
            loadDummyPosts(); // Tải dữ liệu giả nếu API lỗi
            filterAndShowPosts(tabLayout.getSelectedTabPosition());
            return;
        }

        RetrofitClient.getService().getBaiViets().enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    allPosts.clear();
                    for (BaiViet bv : response.body()) {
                        String imageUrl = null;
                        if (bv.hinhAnh != null && !bv.hinhAnh.isEmpty()) {
                            imageUrl = IMAGE_BASE_URL + bv.hinhAnh;
                        }

                        // VÍ DỤ: Logic để hiển thị thanh ủng hộ
                        // Giả sử các bài viết của 'Quỹ Tấm Lòng Vàng' luôn có thanh ủng hộ
                        boolean showDonation = bv.idNguoiDang == 2; // Giả sử ID 2 là của Quỹ

                        NewsPost post = new NewsPost(
                                bv.id,
                                bv.idNguoiDang == 2 ? "Quỹ Tấm Lòng Vàng" : "Người dùng " + bv.idNguoiDang,
                                bv.ngayDang,
                                bv.noiDung,
                                imageUrl,
                                bv.idNguoiDang == 2 ? R.drawable.quy_tamlongvang_avatar : R.drawable.bangtin_avatar_default,
                                bv.soLuotThich,
                                bv.soLuotBinhLuan,
                                showDonation, showDonation ? 25 : 0, showDonation ? 120 : 0, showDonation ? 7 : 0
                        );
                        allPosts.add(post);
                    }
                    filterAndShowPosts(tabLayout.getSelectedTabPosition());
                } else {
                    Toast.makeText(getContext(), "Lỗi server: " + response.code(), Toast.LENGTH_LONG).show();
                    loadDummyPosts();
                    filterAndShowPosts(tabLayout.getSelectedTabPosition());
                }
            }

            @Override
            public void onFailure(Call<List<BaiViet>> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "API không chạy. Hiển thị giao diện xem trước.", Toast.LENGTH_LONG).show();
                loadDummyPosts();
                filterAndShowPosts(tabLayout.getSelectedTabPosition());
            }
        });
    }

    private void loadDummyPosts() { 
        if (getContext() == null) return;
        allPosts.clear();
        String dongBaoMtUri = "android.resource://" + getContext().getPackageName() + "/" + R.drawable.dong_bao_mt;
        String quyenGopKhanCapUri = "android.resource://" + getContext().getPackageName() + "/" + R.drawable.quyen_gop_khan_cap;
        String tinhNguyenVienUri = "android.resource://" + getContext().getPackageName() + "/" + R.drawable.tinh_nguyen_vien_trao_qua;

        allPosts.add(new NewsPost(999, "Quỹ Tấm Lòng Vàng", "Vừa xong", "Chung tay ủng hộ đồng bào miền Trung vượt qua bão lũ...", dongBaoMtUri, R.drawable.quy_tamlongvang_avatar, 1502, 356, true, 25, 120, 7));
        allPosts.add(new NewsPost(998, "Hội Chữ Thập Đỏ", "1 giờ trước", "Kêu gọi quyên góp khẩn cấp cho các tỉnh bị ảnh hưởng bởi lũ lụt...", quyenGopKhanCapUri, R.drawable.hoi_chuthapdo_avatar, 875, 128, true, 0, 0, 15));
        allPosts.add(new NewsPost(997, "Tình nguyện viên An", "Hôm qua", "Hôm nay đoàn chúng mình đã trao tận tay 100 phần quà...", tinhNguyenVienUri, R.drawable.tinh_nguyen_vien_an_avatar, 420, 95, false, 0, 0, 0));
    }

    private void handleLikeClick(int position) {
        if (position < 0 || position >= displayedPostList.size()) return;
        NewsPost post = displayedPostList.get(position);

        post.isLiked = !post.isLiked;
        if (post.isLiked) post.likeCount++; else post.likeCount--;
        adapter.notifyItemChanged(position, "like_status_changed");

        if (post.id >= 990) return; // Không gọi API cho bài viết giả

        RetrofitClient.getService().likePost(post.id).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                if (!isAdded() || response.isSuccessful()) return;
                // Nếu API thất bại, rollback lại trạng thái like
                post.isLiked = !post.isLiked;
                if (post.isLiked) post.likeCount++; else post.likeCount--;
                adapter.notifyItemChanged(position, "like_status_changed");
            }
            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                if (!isAdded()) return;
                 // Nếu API thất bại, rollback lại trạng thái like
                post.isLiked = !post.isLiked;
                if (post.isLiked) post.likeCount++; else post.likeCount--;
                adapter.notifyItemChanged(position, "like_status_changed");
            }
        });
    }

    private void handleCommentClick(int position) {
        if (position < 0 || position >= displayedPostList.size() || getActivity() == null) return;
        Intent intent = new Intent(getActivity(), BangTinCommentActivity.class);
        intent.putExtra("post_position", position);
        intent.putExtra("post_data", displayedPostList.get(position));
        commentLauncher.launch(intent);
    }

    private void handleShareClick(int position) {
        if (position < 0 || position >= displayedPostList.size()) return;
        try {
            NewsPost post = displayedPostList.get(position);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Bài viết được chia sẻ từ " + getString(R.string.app_name));
            shareIntent.putExtra(Intent.EXTRA_TEXT, post.content);
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Không thể chia sẻ bài viết này", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleDonateClick(int position) {
        if (position < 0 || position >= displayedPostList.size() || getActivity() == null) return;
        NewsPost post = displayedPostList.get(position);
        Intent intent = new Intent(getActivity(), ChuyenTienActivity.class);
        intent.putExtra("POST_ID", post.id);
        intent.putExtra("RECEIVER_NAME", post.author);
        startActivity(intent);
    }

    private void showCreatePostDialog() {
         if (getContext() == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_post, null);
        EditText edt = dialogView.findViewById(R.id.edt_create_post_content);
        ImageView btnAddPhoto = dialogView.findViewById(R.id.btn_add_photo);
        dialogImgSelectedPhoto = dialogView.findViewById(R.id.img_selected_photo);
        dialogBtnRemovePhoto = dialogView.findViewById(R.id.btn_remove_photo);

        selectedImageUri = null;
        dialogImgSelectedPhoto.setImageURI(null);
        dialogImgSelectedPhoto.setVisibility(View.GONE);
        dialogBtnRemovePhoto.setVisibility(View.GONE);

        btnAddPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        dialogBtnRemovePhoto.setOnClickListener(v -> {
            selectedImageUri = null;
            dialogImgSelectedPhoto.setImageURI(null);
            dialogImgSelectedPhoto.setVisibility(View.GONE);
            dialogBtnRemovePhoto.setVisibility(View.GONE);
        });

        builder.setView(dialogView)
                .setTitle("Chia sẻ điều gì đó...")
                .setPositiveButton("Đăng", (d, w) -> {
                    String content = edt.getText().toString().trim();
                    if (!content.isEmpty() || selectedImageUri != null) {
                        // TODO: Thay thế '1' bằng ID người dùng thật sự từ SharedPreferences hoặc ViewModel
                        createPostOnServer(1, content, selectedImageUri);
                    } else {
                        Toast.makeText(getContext(), "Nhập nội dung hoặc chọn ảnh đi nào!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void createPostOnServer(int idNguoiDang, String noiDung, @Nullable Uri imageUri) {
        // Code này giữ nguyên, đã được tối ưu
    }

    private void addNewPostToView(CreatePostResponse postResponse, String noiDung, int idNguoiDang) {
       // Code này giữ nguyên
    }

    private void showPostOptionsMenu(final int position, View anchorView) {
       // Code này giữ nguyên
    }

    private void deletePost(final int position) {
       // Code này giữ nguyên
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        // Code này giữ nguyên
        return null;
    }

    private String getFileName(Uri uri) {
        // Code này giữ nguyên
        return null;
    }

    private String getMimeType(Uri uri) {
        // Code này giữ nguyên
        return null;
    }
}

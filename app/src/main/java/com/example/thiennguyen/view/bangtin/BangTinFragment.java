package com.example.thiennguyen.view.bangtin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
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

    private static final int PICK_IMAGE_REQUEST = 101;
    private static final int COMMENT_REQUEST_CODE = 100;
    private RecyclerView recyclerView;
    private NewsPostAdapter adapter;
    private ArrayList<NewsPost> displayedPostList;
    private ArrayList<NewsPost> allPosts;
    private Uri selectedImageUri;
    private ImageView dialogImgSelectedPhoto;
    private ImageView dialogBtnRemovePhoto;
    private TabLayout tabLayout;
    private TextView tvEmptyView;

    private static final String IMAGE_BASE_URL = "http://192.168.1.81:5089";

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }

        if (requestCode == COMMENT_REQUEST_CODE) {
            int position = data.getIntExtra("post_position", -1);
            if (position != -1 && position < displayedPostList.size()) {
                displayedPostList.get(position).commentCount++;
                adapter.notifyItemChanged(position, "comment_count_changed");
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            selectedImageUri = data.getData();
            if (dialogImgSelectedPhoto != null && dialogBtnRemovePhoto != null && selectedImageUri != null) {
                dialogImgSelectedPhoto.setImageURI(selectedImageUri);
                dialogImgSelectedPhoto.setVisibility(View.VISIBLE);
                dialogBtnRemovePhoto.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bang_tin, container, false);

        recyclerView = view.findViewById(R.id.bangtin_recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tvEmptyView = view.findViewById(R.id.bangtin_tv_empty_view);

        allPosts = new ArrayList<>();
        displayedPostList = new ArrayList<>();
        adapter = new NewsPostAdapter(displayedPostList);
        recyclerView.setAdapter(adapter);

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
        });

        loadDummyPosts();

        view.findViewById(R.id.bangtin_fab_create_post).setOnClickListener(v -> showCreatePostDialog());
        view.findViewById(R.id.bangtin_btn_notification).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ThongBaoActivity.class);
            startActivity(intent);
        });

        tabLayout = view.findViewById(R.id.bangtin_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Mới nhất"));
        tabLayout.addTab(tabLayout.newTab().setText("Nổi bật"));
        tabLayout.addTab(tabLayout.newTab().setText("Đang theo dõi"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAndShowPosts(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) { }
            @Override public void onTabReselected(TabLayout.Tab tab) { }
        });

        filterAndShowPosts(0);

        return view;
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
                        .filter(p -> p.likeCount > 1000)
                        .collect(Collectors.toList());
                displayedPostList.addAll(trendingPosts);
                emptyMessage = "Chưa có bài viết nào nổi bật.";
                break;
            case 2: // Đang theo dõi
                emptyMessage = "Bạn chưa theo dõi một cá nhân hay tổ chức nào.";
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
        if (RetrofitClient.getService() == null) {
            Toast.makeText(getContext(), "Không khởi tạo được API", Toast.LENGTH_LONG).show();
            return;
        }

        RetrofitClient.getService().getBaiViets().enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    allPosts.clear();
                    for (BaiViet bv : response.body()) {
                        String thoiGian = bv.ngayDang != null ? bv.ngayDang : "Vừa xong";
                        String noiDung = bv.noiDung != null ? bv.noiDung : "";
                        String imageUrl = null;
                        if (bv.hinhAnh != null && !bv.hinhAnh.isEmpty()) {
                            imageUrl = IMAGE_BASE_URL + bv.hinhAnh;
                        }

                        NewsPost post = new NewsPost(
                                bv.id,
                                "Người dùng " + bv.idNguoiDang,
                                thoiGian,
                                noiDung,
                                imageUrl,
                                R.drawable.bangtin_avatar_default,
                                bv.soLuotThich,
                                bv.soLuotBinhLuan
                        );
                        allPosts.add(0, post);
                    }
                    filterAndShowPosts(tabLayout.getSelectedTabPosition());
                } else {
                    Toast.makeText(getContext(), "Lỗi server: " + response.code(), Toast.LENGTH_LONG).show();
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

        allPosts.add(new NewsPost(
                999,
                "Quỹ Tấm Lòng Vàng",
                "Vừa xong",
                "Chung tay ủng hộ đồng bào miền Trung vượt qua bão lũ. Mỗi đóng góp của bạn đều là niềm hy vọng cho những gia đình đang gặp khó khăn.",
                dongBaoMtUri,
                R.drawable.quy_tamlongvang_avatar,
                1502,
                356
        ));
        allPosts.add(new NewsPost(
                998,
                "Hội Chữ Thập Đỏ",
                "1 giờ trước",
                "Kêu gọi quyên góp khẩn cấp cho các tỉnh bị ảnh hưởng bởi lũ lụt. Hiện chúng tôi đang cần quần áo, lương thực và nước uống sạch. Xin hãy cùng lan tỏa!",
                quyenGopKhanCapUri,
                R.drawable.hoi_chuthapdo_avatar,
                875,
                128
        ));
        allPosts.add(new NewsPost(
                997,
                "Tình nguyện viên An",
                "Hôm qua",
                "Hôm nay đoàn chúng mình đã trao tận tay 100 phần quà cho các hộ dân tại xã X, huyện Y. Cảm ơn sự đóng góp của tất cả mọi người!",
                tinhNguyenVienUri,
                R.drawable.tinh_nguyen_vien_an_avatar,
                420,
                95
        ));
    }

    private void handleLikeClick(int position) {
        if (position < 0 || position >= displayedPostList.size()) return;

        NewsPost post = displayedPostList.get(position);

        if (post.id >= 990) {
            post.isLiked = !post.isLiked;
            if (post.isLiked) post.likeCount++;
            else post.likeCount--;
            adapter.notifyItemChanged(position, "like_status_changed");
            return;
        }

        post.isLiked = !post.isLiked;
        if (post.isLiked) {
            post.likeCount++;
        } else {
            post.likeCount--;
        }
        adapter.notifyItemChanged(position, "like_status_changed");

        RetrofitClient.getService().likePost(post.id).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                if (!response.isSuccessful()) {
                    post.isLiked = !post.isLiked;
                    if (post.isLiked) {
                        post.likeCount++;
                    } else {
                        post.likeCount--;
                    }
                    adapter.notifyItemChanged(position, "like_status_changed");
                    Toast.makeText(getContext(), "Lỗi khi thích bài viết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                post.isLiked = !post.isLiked;
                if (post.isLiked) {
                    post.likeCount++;
                } else {
                    post.likeCount--;
                }
                adapter.notifyItemChanged(position, "like_status_changed");
                Toast.makeText(getContext(), "Lỗi mạng, không thể thích", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleCommentClick(int position) {
        if (position < 0 || position >= displayedPostList.size()) return;

        Intent intent = new Intent(getActivity(), BangTinCommentActivity.class);
        intent.putExtra("post_position", position);
        intent.putExtra("post_data", displayedPostList.get(position));
        startActivityForResult(intent, COMMENT_REQUEST_CODE);
    }

    private void handleShareClick(int position) {
        if (position < 0 || position >= displayedPostList.size()) return;
        try {
            NewsPost post = displayedPostList.get(position);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = post.content;
            String shareSub = "Bài viết được chia sẻ từ ứng dụng của tôi";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Không thể chia sẻ bài viết này", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCreatePostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
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
                        createPostOnServer(1, content, selectedImageUri);
                    } else {
                        Toast.makeText(getContext(), "Nhập nội dung hoặc chọn ảnh đi nào!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void createPostOnServer(int idNguoiDang, String noiDung, @Nullable Uri imageUri) {
        Call<CreatePostResponse> call;

        if (imageUri == null) {
            CreatePostRequest request = new CreatePostRequest(idNguoiDang, noiDung);
            call = RetrofitClient.getService().createBaiViet(request);
        } else {
            if (getContext() == null) return;

            RequestBody idNguoiDangPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idNguoiDang));
            RequestBody noiDungPart = RequestBody.create(MediaType.parse("text/plain"), noiDung);
            MultipartBody.Part filePart = null;

            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                if (inputStream != null) {
                    byte[] fileBytes = getBytes(inputStream);
                    String mimeType = getMimeType(imageUri);
                    String fileName = getFileName(imageUri);

                    if (mimeType != null && fileName != null) {
                        RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), fileBytes);
                        filePart = MultipartBody.Part.createFormData("File", fileName, fileBody);
                    } else {
                        Toast.makeText(getContext(), "Không thể lấy thông tin tệp tin.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(getContext(), "Không thể mở tệp hình ảnh.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Lỗi khi đọc tệp hình ảnh.", Toast.LENGTH_SHORT).show();
                return;
            }
            call = RetrofitClient.getService().createBaiViet(idNguoiDangPart, noiDungPart, filePart);
        }

        call.enqueue(new Callback<CreatePostResponse>() {
            @Override
            public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Đăng thành công!", Toast.LENGTH_SHORT).show();
                    addNewPostToView(response.body(), noiDung, idNguoiDang);
                } else {
                    Toast.makeText(getContext(), "Đăng bài thất bại, mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addNewPostToView(CreatePostResponse postResponse, String noiDung, int idNguoiDang) {
        String imageUrl = null;
        if (postResponse.hinhAnh != null && !postResponse.hinhAnh.isEmpty()) {
            imageUrl = IMAGE_BASE_URL + postResponse.hinhAnh;
        }

        String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        NewsPost newPost = new NewsPost(
                postResponse.id,
                "Người dùng " + idNguoiDang,
                currentTime,
                noiDung,
                imageUrl,
                R.drawable.bangtin_avatar_default,
                0,
                0
        );

        allPosts.add(0, newPost);
        filterAndShowPosts(tabLayout.getSelectedTabPosition());
        recyclerView.scrollToPosition(0);
    }

    private void showPostOptionsMenu(final int position, View anchorView) {
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        popup.getMenuInflater().inflate(R.menu.post_options_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_delete_post) {
                if (displayedPostList.get(position).id >= 990) {
                    Toast.makeText(getContext(), "Không thể xóa bài viết xem trước.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa bài viết này?")
                        .setPositiveButton("Xóa", (dialog, which) -> deletePost(position))
                        .setNegativeButton("Hủy", null)
                        .show();
                return true;
            } else if (itemId == R.id.action_edit_post) {
                Toast.makeText(getContext(), "Chức năng chỉnh sửa đang được phát triển", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.action_report_post) {
                Toast.makeText(getContext(), "Cảm ơn bạn đã báo cáo bài viết!", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void deletePost(final int position) {
        if (position < 0 || position >= displayedPostList.size()) return;

        NewsPost postToDelete = displayedPostList.get(position);
        int postId = postToDelete.id;

        allPosts.remove(postToDelete);
        filterAndShowPosts(tabLayout.getSelectedTabPosition());
        
        Call<Void> call = RetrofitClient.getService().deleteBaiViet(postId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!isAdded()) return;

                if (response.isSuccessful()) {
                     Toast.makeText(getContext(), "Đã xóa bài viết", Toast.LENGTH_SHORT).show();
                } else {
                    allPosts.add(postToDelete);
                    filterAndShowPosts(tabLayout.getSelectedTabPosition());
                    String errorDetails = "Lỗi " + response.code() + ": " + response.message() + "\nURL: " + call.request().url();
                    Toast.makeText(getContext(), errorDetails, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!isAdded()) return;
                 allPosts.add(postToDelete);
                 filterAndShowPosts(tabLayout.getSelectedTabPosition());
                Toast.makeText(getContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private String getFileName(Uri uri) {
        if (getContext() == null) return null;
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String getMimeType(Uri uri) {
        if (getContext() == null) return null;
        String mimeType;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = getContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }
}

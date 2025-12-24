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
import android.widget.CheckBox;
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
import com.example.thiennguyen.api.bangtin.CreatePostResponse;
import com.example.thiennguyen.api.bangtin.LikeCommentResponse;
import com.example.thiennguyen.api.bangtin.NewsPost;
import com.example.thiennguyen.api.bangtin.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BangTinFragment extends Fragment implements NewsPostAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private NewsPostAdapter adapter;
    private ArrayList<NewsPost> displayedPostList;
    private ArrayList<NewsPost> allPosts;
    private Uri selectedImageUri;
    private ImageView dialogImgSelectedPhoto;
    private ImageView dialogBtnRemovePhoto;
    private TabLayout tabLayout;
    private TextView tvEmptyView;

    // Đảm bảo địa chỉ IP này là của máy tính đang chạy backend
    private static final String IMAGE_BASE_URL = "http://192.168.114.213:5089";

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

        loadPostsFromServer();

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
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Tải lại toàn bộ bài viết để cập nhật số lượng comment
                        loadPostsFromServer();
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
        if (tabLayout.getTabCount() == 0) {
            tabLayout.addTab(tabLayout.newTab().setText("Mới nhất"));
            tabLayout.addTab(tabLayout.newTab().setText("Nổi bật"));
            tabLayout.addTab(tabLayout.newTab().setText("Đang theo dõi"));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAndShowPosts(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupClickListeners(View view) {
        adapter.setOnItemClickListener(this);
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
                        .filter(p -> p.likeCount > 50)
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
        if (RetrofitClient.getService() == null || getContext() == null) {
            return;
        }

        RetrofitClient.getService().getBaiViets().enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    allPosts.clear();
                    for (BaiViet bv : response.body()) {
                        // Xây dựng URL đầy đủ cho hình ảnh
                        String imageUrl = null;
                        if (bv.hinhAnh != null && !bv.hinhAnh.isEmpty()) {
                            imageUrl = IMAGE_BASE_URL + "/images/" + bv.hinhAnh;
                        }

                        boolean showDonation = bv.idChienDich != null;
                        allPosts.add(new NewsPost(bv.id, bv.idChienDich, "Người dùng " + bv.idNguoiDang, bv.ngayDang, bv.noiDung, imageUrl, R.drawable.ic_launcher_background, bv.soLuotThich, bv.soLuotBinhLuan, showDonation, 25, 120, 7));
                    }
                    filterAndShowPosts(tabLayout.getSelectedTabPosition());
                } else {
                    Toast.makeText(getContext(), "Lỗi server: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<BaiViet>> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCommentClick(int position) {
        if (position < 0 || position >= displayedPostList.size() || getActivity() == null) return;
        Intent intent = new Intent(getActivity(), BangTinCommentActivity.class);
        intent.putExtra("post_position", position);
        intent.putExtra("post_data", displayedPostList.get(position));
        commentLauncher.launch(intent);
    }

    @Override
    public void onLikeClick(int position) {
        if (position < 0 || position >= displayedPostList.size()) return;
        NewsPost post = displayedPostList.get(position);

        post.isLiked = !post.isLiked;
        if (post.isLiked) post.likeCount++;
        else post.likeCount--;
        adapter.notifyItemChanged(position, "like_status_changed");

        RetrofitClient.getService().likePost(post.id).enqueue(new Callback<LikeCommentResponse>() {
            @Override
            public void onResponse(Call<LikeCommentResponse> call, Response<LikeCommentResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    post.likeCount = response.body().soLuotThich;
                    adapter.notifyItemChanged(position, "like_status_changed");
                } else {
                    post.isLiked = !post.isLiked;
                    if (post.isLiked) post.likeCount++;
                    else post.likeCount--;
                    adapter.notifyItemChanged(position, "like_status_changed");
                }
            }

            @Override
            public void onFailure(Call<LikeCommentResponse> call, Throwable t) {
                if (!isAdded()) return;
                post.isLiked = !post.isLiked;
                if (post.isLiked) post.likeCount++;
                else post.likeCount--;
                adapter.notifyItemChanged(position, "like_status_changed");
            }
        });
    }

    @Override
    public void onShareClick(int position) {
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

    @Override
    public void onMoreOptionsClick(int position, View view) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.getMenuInflater().inflate(R.menu.post_options_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_delete_post) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Xóa bài viết")
                        .setMessage("Bạn có chắc chắn muốn xóa bài viết này không?")
                        .setPositiveButton("Xóa", (dialog, which) -> deletePost(position))
                        .setNegativeButton("Hủy", null)
                        .show();
                return true;
            }
            return false;
        });
        popup.show();
    }

    @Override
    public void onDonateClick(int position) {
        if (position < 0 || position >= displayedPostList.size() || getActivity() == null) return;
        NewsPost post = displayedPostList.get(position);
        Intent intent = new Intent(getActivity(), ChuyenTienActivity.class);
        intent.putExtra("POST_ID", post.id);
        intent.putExtra("RECEIVER_NAME", post.author);
        // Thêm ID chiến dịch vào Intent
        if (post.campaignId != null) {
            intent.putExtra("CAMPAIGN_ID", post.campaignId);
        }
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
        final CheckBox chkCallForDonation = dialogView.findViewById(R.id.chk_call_for_donation);

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
                    boolean isCallingForDonation = chkCallForDonation.isChecked();
                    if (!content.isEmpty() || selectedImageUri != null) {
                        createPostOnServer(1, content, selectedImageUri, isCallingForDonation);
                    } else {
                        Toast.makeText(getContext(), "Nhập nội dung hoặc chọn ảnh đi nào!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void createPostOnServer(int idNguoiDang, String noiDung, @Nullable Uri imageUri, boolean isCallingForDonation) {
        if (RetrofitClient.getService() == null || getContext() == null) return;

        RequestBody idNguoiDangPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idNguoiDang));
        RequestBody noiDungPart = RequestBody.create(MediaType.parse("text/plain"), noiDung);
        
        // SỬA LỖI: Gửi 1 hoặc 0 thay vì "true"/"false"
        String isCallingValue = isCallingForDonation ? "1" : "0";
        RequestBody isCallingPart = RequestBody.create(MediaType.parse("text/plain"), isCallingValue);
        
        MultipartBody.Part filePart = null;

        if (imageUri != null) {
            try (InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri)) {
                if (inputStream == null) throw new IOException("Không thể mở InputStream từ Uri");

                byte[] fileBytes = getBytes(inputStream);
                String mimeType = getMimeType(imageUri);
                String fileName = getFileName(imageUri);

                if (mimeType == null || fileName == null) throw new IOException("Không thể lấy thông tin tệp tin.");

                RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), fileBytes);
                filePart = MultipartBody.Part.createFormData("File", fileName, fileBody);

            } catch (IOException e) {
                Toast.makeText(getContext(), "Lỗi khi xử lý tệp hình ảnh.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Call<CreatePostResponse> call = RetrofitClient.getService().createBaiViet(idNguoiDangPart, noiDungPart, isCallingPart, filePart);

        call.enqueue(new Callback<CreatePostResponse>() {
            @Override
            public void onResponse(Call<CreatePostResponse> call, Response<CreatePostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Đăng thành công!", Toast.LENGTH_SHORT).show();
                    loadPostsFromServer();
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

    private void deletePost(final int position) {
        // TODO: Gọi API để xóa bài viết trên server
        displayedPostList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, displayedPostList.size());
        Toast.makeText(getContext(), "Đã xóa bài viết", Toast.LENGTH_SHORT).show();
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
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }
}

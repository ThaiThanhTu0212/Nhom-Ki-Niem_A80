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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thiennguyen.R;
import com.example.thiennguyen.api.bangtin.BaiViet;
import com.example.thiennguyen.api.bangtin.NewsPost;
import com.example.thiennguyen.api.bangtin.NewsPostAdapter;
import com.example.thiennguyen.api.bangtin.RetrofitClient;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BangTinFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 101;
    private RecyclerView recyclerView;
    private NewsPostAdapter adapter;
    private ArrayList<NewsPost> postList;
    private Uri selectedImageUri;
    private ImageView dialogImgSelectedPhoto;
    private ImageView dialogBtnRemovePhoto;


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }

        if (requestCode == 100) { // Result from Comment Activity
            int position = data.getIntExtra("post_position", -1);
            int newCount = data.getIntExtra("new_comment_count", 0);
            if (position != -1 && position < postList.size()) {
                postList.get(position).commentCount = newCount;
                adapter.notifyItemChanged(position);
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) { // Result from Image Picker
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

        // === RecyclerView ===
        recyclerView = view.findViewById(R.id.bangtin_recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        adapter = new NewsPostAdapter(postList);
        recyclerView.setAdapter(adapter);

        // === SỬA LẠI LOGIC CLICK CHO ĐÚNG ===
        adapter.setOnItemClickListener(new NewsPostAdapter.OnItemClickListener() {
            @Override
            public void onCommentClick(int position) {
                // Mở màn hình bình luận
                Intent intent = new Intent(getActivity(), BangTinCommentActivity.class);
                intent.putExtra("post_position", position);
                intent.putExtra("post_data", postList.get(position));
                startActivityForResult(intent, 100);
            }

            @Override
            public void onLikeClick(int position) {
                // Xử lý logic thích ở đây
                Toast.makeText(getContext(), "Đã thích bài viết!", Toast.LENGTH_SHORT).show();
                // Ví dụ: postList.get(position).isLiked = !postList.get(position).isLiked;
                // adapter.notifyItemChanged(position);
            }
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
        if (RetrofitClient.getService() == null) {
            Toast.makeText(getContext(), "Không khởi tạo được API", Toast.LENGTH_LONG).show();
            return;
        }

        RetrofitClient.getService().getBaiViets().enqueue(new Callback<List<BaiViet>>() {
            @Override
            public void onResponse(Call<List<BaiViet>> call, Response<List<BaiViet>> response) {
                if (!isAdded()) return;

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

    // GỬI BÀI LÊN SERVER (ĐÃ SỬA)
    private void createPostOnServer(int idNguoiDang, String noiDung, @Nullable Uri imageUri) {
        RequestBody idNguoiDangPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idNguoiDang));
        RequestBody noiDungPart = RequestBody.create(MediaType.parse("text/plain"), noiDung);
        MultipartBody.Part filePart = null;

        if (imageUri != null && getContext() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri);
                if (inputStream != null) {
                    byte[] fileBytes = getBytes(inputStream);
                    String mimeType = getMimeType(imageUri);
                    String fileName = getFileName(imageUri);

                    if (mimeType != null && fileName != null) {
                        RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), fileBytes);
                        filePart = MultipartBody.Part.createFormData("file", fileName, fileBody);
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
        }

        RetrofitClient.getService().createBaiViet(idNguoiDangPart, noiDungPart, filePart).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Đăng thành công!", Toast.LENGTH_SHORT).show();
                    loadPostsFromServer(); // reload ngay
                } else {
                    Toast.makeText(getContext(), "Đăng bài thất bại, mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Phương thức trợ giúp để đọc bytes từ InputStream
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

    // Phương thức trợ giúp để lấy tên tệp từ Uri
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

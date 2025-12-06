package com.example.thiennguyen.view.ungho;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.thiennguyen.R;
import com.example.thiennguyen.view.model.ChienDich;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ChiTietChienDichFragment extends Fragment {

    private static final String ARG_CHIEN_DICH = "chien_dich";
    private ChienDich chienDich;

    public ChiTietChienDichFragment() {
        // Required empty public constructor
    }

    public static ChiTietChienDichFragment newInstance(ChienDich chienDich) {
        ChiTietChienDichFragment fragment = new ChiTietChienDichFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CHIEN_DICH, chienDich);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chienDich = getArguments().getParcelable(ARG_CHIEN_DICH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_chien_dich, container, false);

        if (chienDich != null) {
            RecyclerView imageRecyclerView = view.findViewById(R.id.imageRecyclerView);
            ImageAdapter imageAdapter = new ImageAdapter(getContext(), getSampleImageUrls());
            imageRecyclerView.setAdapter(imageAdapter);

            TextView tvTenChienDich = view.findViewById(R.id.tvTenChienDich);
            TextView tvTenToChuc = view.findViewById(R.id.tvTenToChuc);
            TextView tvMoTa = view.findViewById(R.id.tvMoTa);
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            TextView tvSoTienHienTai = view.findViewById(R.id.tvSoTienHienTai);
            TextView tvSoTienMucTieu = view.findViewById(R.id.tvSoTienMucTieu);
            Button btnUngHo = view.findViewById(R.id.btnUngHo);

            tvTenChienDich.setText(chienDich.getTenCd());
            tvTenToChuc.setText(chienDich.getNguoiToChuc().getHoTen());
            tvMoTa.setText(chienDich.getMoTa());

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            tvSoTienHienTai.setText(currencyFormat.format(chienDich.getSoTienHienTai()));
            tvSoTienMucTieu.setText("trên " + currencyFormat.format(chienDich.getSoTienMucTieu()));

            int progress = chienDich.getSoTienHienTai().multiply(new java.math.BigDecimal(100))
                    .divide(chienDich.getSoTienMucTieu(), java.math.RoundingMode.HALF_UP).intValue();
            progressBar.setProgress(progress);

            btnUngHo.setOnClickListener(v -> showQrDialog());
        }

        return view;
    }

    private List<String> getSampleImageUrls() {
        return Arrays.asList(
                "https://i.pinimg.com/564x/b8/69/27/b8692797a22459434bedb93301077334.jpg",
                "https://i.pinimg.com/564x/4b/7e/37/4b7e373b8a2e58a23351945a8f579d4b.jpg",
                "https://i.pinimg.com/564x/a2/45/63/a24563a61f3f615f3f4e2f3d61172c1c.jpg",
                "https://i.pinimg.com/564x/d1/a7/6b/d1a76b0f1d82e2e7a4f89a9f2d15024b.jpg",
                "https://i.pinimg.com/564x/e7/7c/49/e77c49b145e1b1d1f7c3c3a9f73315a2.jpg"
        );
    }

    private void showQrDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_qr_code);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView imgCloseDialog = dialog.findViewById(R.id.imgCloseDialog);
        imgCloseDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}

package com.example.thiennguyen.view.ungho;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
            ImageView imgChienDich = view.findViewById(R.id.imgChienDich);
            TextView tvTenChienDich = view.findViewById(R.id.tvTenChienDich);
            TextView tvTenToChuc = view.findViewById(R.id.tvTenToChuc);
            TextView tvMoTa = view.findViewById(R.id.tvMoTa);
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            TextView tvSoTienHienTai = view.findViewById(R.id.tvSoTienHienTai);
            TextView tvSoTienMucTieu = view.findViewById(R.id.tvSoTienMucTieu);
            Button btnUngHo = view.findViewById(R.id.btnUngHo);

            Glide.with(this).load(chienDich.getHinhAnh()).into(imgChienDich);
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

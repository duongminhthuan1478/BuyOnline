package com.example.dell.buyonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.model.SanPham;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dell on 08-Mar-18.
 */

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SanPham> mArraySanPham = new ArrayList<>();

    public DienThoaiAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        mContext = context;
        mArraySanPham = arraySanPham;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sanpham, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mArraySanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView hinhAnhSanPham;
        private TextView textTenSanPham, textGiaSanPham, textMoTaSanPham;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            hinhAnhSanPham = itemView.findViewById(R.id.imageview_phone);
            textGiaSanPham = itemView.findViewById(R.id.text_gia_phone);
            textTenSanPham = itemView.findViewById(R.id.text_name_phone);
            textMoTaSanPham = itemView.findViewById(R.id.text_mo_ta_phone);
        }

        public void setData(int position) {
            SanPham sanPham = mArraySanPham.get(position);
            textTenSanPham.setText(sanPham.getTenSanPham());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            textGiaSanPham.setText(
                    "Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " VND");
            textMoTaSanPham.setMaxLines(2);
            textMoTaSanPham.setEllipsize(TextUtils.TruncateAt.END);
            textMoTaSanPham.setText(sanPham.getMotaSanPham());
            Picasso.with(mContext)
                    .load(sanPham.getHinhAnhSanPham())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(hinhAnhSanPham);
        }
    }
}
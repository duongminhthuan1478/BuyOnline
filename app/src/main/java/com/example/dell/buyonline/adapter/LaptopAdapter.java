package com.example.dell.buyonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.model.SanPham;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<SanPham> mArrayLapTop = new ArrayList<>();

    public LaptopAdapter(Context context, ArrayList<SanPham> arrayLapTop) {
        mContext = context;
        mArrayLapTop = arrayLapTop;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mArrayLapTop.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView hinhAnhSanPham;
        private TextView textTenSanPham, textGiaSanPham, textMoTaSanPham;

        public ViewHolder(View itemView) {
            super(itemView);
            hinhAnhSanPham = itemView.findViewById(R.id.imageview_phone);
            textGiaSanPham = itemView.findViewById(R.id.text_gia_phone);
            textTenSanPham = itemView.findViewById(R.id.text_name_phone);
            textMoTaSanPham = itemView.findViewById(R.id.text_mo_ta_phone);
        }
        public void setData(int position) {
            SanPham sanPham = mArrayLapTop.get(position);
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

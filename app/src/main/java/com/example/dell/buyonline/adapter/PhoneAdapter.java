package com.example.dell.buyonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.model.SanPham;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<SanPham> mArrayDienThoai = new ArrayList<>();

    public PhoneAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        mContext = context;
        mArrayDienThoai = arrayDienThoai;
    }

    @Override
    public int getCount() {
        return mArrayDienThoai.size();

    }

    @Override
    public Object getItem(int position) {
        return mArrayDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        private ImageView hinhAnhSanPham;
        private TextView textTenSanPham, textGiaSanPham, textMoTaSanPham;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.san_pham_item, null);
            viewHolder.textTenSanPham = (TextView) view.findViewById(R.id.text_name_phone);
            viewHolder.textGiaSanPham = (TextView) view.findViewById(R.id.text_gia_phone);
            viewHolder.textMoTaSanPham = (TextView) view.findViewById(R.id.text_mo_ta_phone);
            viewHolder.hinhAnhSanPham = (ImageView) view.findViewById(R.id.imageview_phone);
            view.setTag(viewHolder);
        } else {
            SanPham sanPham = (SanPham) getItem(position);
            viewHolder.textTenSanPham.setText(sanPham.getTenSanPham());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            viewHolder.textGiaSanPham.setText(
                    "Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " VND");
            viewHolder.textMoTaSanPham.setMaxLines(3);
            viewHolder.textMoTaSanPham.setEllipsize(TextUtils.TruncateAt.END);
            viewHolder.textMoTaSanPham.setText(sanPham.getMotaSanPham());
            Picasso.with(mContext)
                    .load(sanPham.getHinhAnhSanPham())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(viewHolder.hinhAnhSanPham);
        }
        return view;
    }

}

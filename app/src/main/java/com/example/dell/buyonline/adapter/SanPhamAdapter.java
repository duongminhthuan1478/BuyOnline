package com.example.dell.buyonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.buyonline.R;
import com.example.dell.buyonline.model.SanPham;
import com.example.dell.buyonline.ultil.OnItemClickListener;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dell on 07-Mar-18.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<SanPham> mArraySanPham = new ArrayList<>();
    private OnItemClickListener mClickListener;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        mContext = context;
        mArraySanPham = arraySanPham;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.san_pham_moi_nhat_item,parent, false);
        return new ViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //SanPham sanPham = mArraySanPham.get(position);
        //textTenSanPham.setText(sanPham.getTenSanPham());
        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return mArraySanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView hinhAnhSanPham;
        private TextView textTenSanPham, textGiaSanPham;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            hinhAnhSanPham = itemView.findViewById(R.id.imageview_san_pham);
            textGiaSanPham = itemView.findViewById(R.id.text_gia_sp);
            textTenSanPham = itemView.findViewById(R.id.text_ten_sp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void setData(int position) {
            SanPham sanPham = mArraySanPham.get(position);
            textTenSanPham.setText(sanPham.getTenSanPham());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            textGiaSanPham.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " VND");
            Picasso.with(mContext).load(sanPham.getHinhAnhSanPham())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(hinhAnhSanPham);
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mClickListener = listener;
    }
}
